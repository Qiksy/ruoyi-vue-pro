package cn.iocoder.yudao.module.sale.service.competeinfosub;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileMapper;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.competeinfosub.CompeteInfoSubMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 竞品信息子 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
public class CompeteInfoSubServiceImpl implements CompeteInfoSubService {

    @Resource
    private CompeteInfoSubMapper competeInfoSubMapper;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createCompeteInfoSub(CompeteInfoSubSaveReqVO createReqVO) {
        // 插入
        CompeteInfoSubDO competeInfoSub = BeanUtils.toBean(createReqVO, CompeteInfoSubDO.class);
        competeInfoSubMapper.insert(competeInfoSub);
        // 返回
        return competeInfoSub.getId();
    }

    @Override
    public void updateCompeteInfoSub(CompeteInfoSubSaveReqVO updateReqVO) {
        // 校验存在
        validateCompeteInfoSubExists(updateReqVO.getId());
        // 更新
        CompeteInfoSubDO updateObj = BeanUtils.toBean(updateReqVO, CompeteInfoSubDO.class);
        competeInfoSubMapper.updateById(updateObj);
    }

    @Override
    public void deleteCompeteInfoSub(Long id) {
        // 校验存在
        validateCompeteInfoSubExists(id);
        // 删除
        competeInfoSubMapper.deleteById(id);
    }

    private void validateCompeteInfoSubExists(Long id) {
        if (competeInfoSubMapper.selectById(id) == null) {
            throw exception(COMPETE_INFO_SUB_NOT_EXISTS);
        }
    }

    @Override
    public CompeteInfoSubRespVO getCompeteInfoSub(Long id) {
        CompeteInfoSubDO competeInfoSubDO = competeInfoSubMapper.selectById(id);

        String fileId = competeInfoSubDO.getFileId();
        FileDO fileDO = fileMapper.selectById(fileId);
        CompeteInfoSubRespVO bean = BeanUtils.toBean(competeInfoSubDO, CompeteInfoSubRespVO.class);
        bean.setFileInfo(fileDO);

        return bean;
    }

    @Override
    public PageResult<CompeteInfoSubDO> getCompeteInfoSubPage(CompeteInfoSubPageReqVO pageReqVO) {
        return competeInfoSubMapper.selectPage(pageReqVO);
    }

    /**
     * key: parentId
     * value: List<CompeteInfoSubDO>
     *
     * @param parentIds
     * @return
     */
    @Override
    public Map<Long, List<CompeteInfoSubRespVO>> selectMapByCompeteInfoIdList(List<Long> parentIds) {
        if (CollUtil.isEmpty(parentIds)){
            return Collections.emptyMap();
        }

        LambdaQueryWrapper<CompeteInfoSubDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CompeteInfoSubDO::getParentId, parentIds).eq(CompeteInfoSubDO::getDeleted, false).orderByAsc(CompeteInfoSubDO::getChangeDate);

        List<CompeteInfoSubDO> list = competeInfoSubMapper.selectList(queryWrapper);

        //带出附件信息
        Set<String> collect = list.stream().map(CompeteInfoSubDO::getFileId).collect(Collectors.toSet());
        Map<Long,FileDO> fileDOMap = new HashMap<>();
        if (!CollUtil.isEmpty(collect)){
            LambdaQueryWrapper<FileDO> qw = new LambdaQueryWrapper<>();
            qw.in(FileDO::getId, collect).eq(FileDO::getDeleted, false)
                    .select(FileDO::getId,FileDO::getName,FileDO::getUrl,FileDO::getType);
            List<FileDO> fileDOS = fileMapper.selectList(qw);
            fileDOMap = fileDOS.stream().collect(Collectors.toMap(FileDO::getId, fileDO -> fileDO));
        }

        // 带出创建人信息
        Set<Long> userIds = list.stream().map(vo->Long.valueOf(vo.getCreator())).collect(Collectors.toSet());
        List<AdminUserRespDTO> userList = adminUserApi.getUserList(userIds);

        List<CompeteInfoSubRespVO> respVOS = new ArrayList<>();
        for (CompeteInfoSubDO competeInfoSubDO : list) {
            CompeteInfoSubRespVO temp = BeanUtils.toBean(competeInfoSubDO,CompeteInfoSubRespVO.class);
            temp.setCreator(competeInfoSubDO.getCreator());
            respVOS.add(temp);
        }

        for (CompeteInfoSubRespVO respVO : respVOS) {
            FileDO fileDO = fileDOMap.get(Long.valueOf(respVO.getFileId()));
            respVO.setFileInfo(fileDO);

            Optional<String> first = userList.stream().
                    filter(vo -> Objects.equals(vo.getId(), Long.valueOf(respVO.getCreator())))
                    .map(AdminUserRespDTO::getNickname)
                    .findFirst();

            first.ifPresent(
                    respVO::setCreatorName
            );

        }

        return respVOS.stream().collect(Collectors.groupingBy(CompeteInfoSubRespVO::getParentId));
    }
}