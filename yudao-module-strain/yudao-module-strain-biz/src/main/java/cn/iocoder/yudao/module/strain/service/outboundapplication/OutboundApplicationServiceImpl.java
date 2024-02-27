package cn.iocoder.yudao.module.strain.service.outboundapplication;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication.OutboundSubApplicationMapper;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.FreezingTubeStockPreEntryService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication.OutboundApplicationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 出库申请 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OutboundApplicationServiceImpl implements OutboundApplicationService {

    @Resource
    private OutboundApplicationMapper outboundApplicationMapper;

    @Resource
    private OutboundSubApplicationMapper subApplicationMapper;

    @Resource
    private FreezingTubeStockPreEntryService freezingTubeStockPreEntryService;


    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createOutboundApplication(OutboundApplicationCreateReqVO createReqVO) {
        return saveApplication(createReqVO,"save");
    }

    @Override
    public void updateOutboundApplication(OutboundApplicationSaveReqVO updateReqVO) {
        // 校验存在
        validateOutboundApplicationExists(updateReqVO.getId());
        // 更新
        OutboundApplicationDO updateObj = BeanUtils.toBean(updateReqVO, OutboundApplicationDO.class);
        outboundApplicationMapper.updateById(updateObj);
    }

    @Override
    public void deleteOutboundApplication(Long id) {
        // 校验存在
        validateOutboundApplicationExists(id);
        // 删除
        outboundApplicationMapper.deleteById(id);
    }

    private void validateOutboundApplicationExists(Long id) {
        if (outboundApplicationMapper.selectById(id) == null) {
            throw exception(OUTBOUND_APPLICATION_NOT_EXISTS);
        }
    }

    @Override
    public OutboundApplicationDO getOutboundApplication(Long id) {
        return outboundApplicationMapper.selectById(id);
    }

    /**
     * 获取主子表的所有的数据
     *
     * @param id 主键
     * @return 单独的表的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutboundApplicationRespVO getOutboundApplicationVO(Long id) {
        //获取主表数据
        OutboundApplicationDO outboundApplicationDO = outboundApplicationMapper.selectById(id);
        OutboundApplicationRespVO respVO = BeanUtils.toBean(outboundApplicationDO, OutboundApplicationRespVO.class);

        List<OutboundSubApplicationDO> subApplicationDOS = subApplicationMapper.selectList("parent_id", id);

        //获取子表数据
        List<OutboundApplicationSubRespVO> subRespVOS = BeanUtils.toBean(subApplicationDOS, OutboundApplicationSubRespVO.class);


        //获取槽位id
        List<Long> stockIds = subRespVOS.stream().map(OutboundApplicationSubRespVO::getStockId).filter(Objects::nonNull).toList();

        //根据槽位id获取位置信息并设置到
        Map<Long, String> stockPositionStrMap = freezingTubeStockPreEntryService.getStockPositionStrMap(stockIds);
        if (!stockPositionStrMap.isEmpty()){
            for (OutboundApplicationSubRespVO subRespVO : subRespVOS) {
                subRespVO.setPositionStr(stockPositionStrMap.get(subRespVO.getStockId()));
            }
        }
        //还需要设置保存人

        List<Long> specimenIds = subRespVOS.stream().map(OutboundApplicationSubRespVO::getSpecimenId).filter(Objects::nonNull).toList();
        //直接获取样品的saveBy
        List<FreezingTubeStockPreEntryDO> specimen = freezingTubeStockPreEntryMapper.selectList("id", specimenIds);
        Set<Long> collect = specimen.stream().map(FreezingTubeStockPreEntryDO::getSaveBy).filter(Objects::nonNull).collect(Collectors.toSet());
        //用户信息
        List<AdminUserRespDTO> userList = adminUserApi.getUserList(collect);

        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(AdminUserRespDTO::getId, AdminUserRespDTO::getNickname));
        Map<Long, Long> specimenMap = specimen.stream().collect(Collectors.toMap(FreezingTubeStockPreEntryDO::getId, FreezingTubeStockPreEntryDO::getSaveBy));

        for (OutboundApplicationSubRespVO subRespVO : subRespVOS) {
            Long specimenId = subRespVO.getSpecimenId();
            Long userId = specimenMap.get(specimenId);
            String userName = userMap.get(userId);
            subRespVO.setSaveByName(userName);
        }

        respVO.setSubList(subRespVOS);
        return respVO;
    }

    @Override
    public PageResult<OutboundApplicationDO> getOutboundApplicationPage(OutboundApplicationPageReqVO pageReqVO) {
        return outboundApplicationMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAndApplyOutboundApplication(OutboundApplicationCreateReqVO createReqVO) {
        return saveApplication(createReqVO,"apply");
    }

    private Long saveApplication(OutboundApplicationCreateReqVO createReqVO,String type) {
        //保存主子表数据
        OutboundApplicationDO outboundApplicationDO = BeanUtils.toBean(createReqVO, OutboundApplicationDO.class);

        //设置保存人姓名
        Long userId = Objects.requireNonNull(SecurityFrameworkUtils.getLoginUser()).getId();
        String nickname = adminUserApi.getUser(userId).getNickname();
        outboundApplicationDO.setApplicant(nickname);

        //设置审批状态
        //设置主表状态为处理中

        if (type.equals("apply")){
            //需要设置审批状态为处理中
            outboundApplicationDO.setApproResult(BpmProcessInstanceResultEnum.PROCESS.getResult().toString());
        }else if (type.equals("save")){
            //需要设置审批状态为未开始
            outboundApplicationDO.setApproResult(BpmProcessInstanceResultEnum.UN_START.getResult().toString());
        }

        //设置编码
        String codePrefix = "CK";
        //获取当前时间
        String now = DateUtil.format(new Date(), "yyyyMMdd");
        //查询数据库已经有的流水号
        LambdaQueryWrapper<OutboundApplicationDO> queryWrapper = new LambdaQueryWrapper<OutboundApplicationDO>()
                .likeRight(OutboundApplicationDO::getCode, codePrefix + now)
                .orderByDesc(OutboundApplicationDO::getCode)
                .eq(OutboundApplicationDO::getDeleted, 0)
                .last("LIMIT 1");
        OutboundApplicationDO tempDO = outboundApplicationMapper.selectOne(queryWrapper);
        //如果没有查询到数据，说明是当天第一条数据
        if (tempDO == null) {
            outboundApplicationDO.setCode(codePrefix + now + "001");
        } else {
            //如果查询到数据，说明不是当天第一条数据
            String code = tempDO.getCode();
            String suffix = code.substring(code.length() - 3);
            int num = Integer.parseInt(suffix) + 1;
            String newSuffix = String.format("%03d", num);
            outboundApplicationDO.setCode(codePrefix + now + newSuffix);
        }

        //插入数据
        outboundApplicationMapper.insert(outboundApplicationDO);



        List<OutboundSubApplicationDO> subApplicationDOS = BeanUtils.toBean(createReqVO.getSubList(), OutboundSubApplicationDO.class);
        //设置子表的父id
        for (OutboundSubApplicationDO subApplicationDO : subApplicationDOS) {
            subApplicationDO.setParentId(outboundApplicationDO.getId());
        }


        //todo 如果是审批的逻辑，需要检查是否已经存在相同的明细正在处理中


        //后续需要出库的时候，需要扫码核验，否则不可以出库
        subApplicationMapper.insertBatch(subApplicationDOS);
        return outboundApplicationDO.getId();
    }
}