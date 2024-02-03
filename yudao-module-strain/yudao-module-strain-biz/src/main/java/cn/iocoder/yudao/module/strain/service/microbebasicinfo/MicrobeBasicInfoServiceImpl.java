package cn.iocoder.yudao.module.strain.service.microbebasicinfo;

import cn.iocoder.yudao.module.strain.dal.mysql.culturemediumdatainfo.CultureMediumDataInfoMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 菌种信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MicrobeBasicInfoServiceImpl implements MicrobeBasicInfoService {

    @Resource
    private MicrobeBasicInfoMapper microbeBasicInfoMapper;

    @Resource
    private CultureMediumDataInfoMapper cultureMediumDataInfoMapper;

    @Override
    public Long createMicrobeBasicInfo(MicrobeBasicInfoSaveReqVO createReqVO) {
        // 插入
        MicrobeBasicInfoDO microbeBasicInfo = BeanUtils.toBean(createReqVO, MicrobeBasicInfoDO.class);
        microbeBasicInfoMapper.insert(microbeBasicInfo);
        // 返回
        return microbeBasicInfo.getId();
    }

    @Override
    public void updateMicrobeBasicInfo(MicrobeBasicInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateMicrobeBasicInfoExists(updateReqVO.getId());
        // 更新
        MicrobeBasicInfoDO updateObj = BeanUtils.toBean(updateReqVO, MicrobeBasicInfoDO.class);
        microbeBasicInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteMicrobeBasicInfo(Long id) {
        // 校验存在
        validateMicrobeBasicInfoExists(id);
        // 删除
        microbeBasicInfoMapper.deleteById(id);
    }

    private void validateMicrobeBasicInfoExists(Long id) {
        if (microbeBasicInfoMapper.selectById(id) == null) {
            throw exception(MICROBE_BASIC_INFO_NOT_EXISTS);
        }
    }

    @Override
    public MicrobeBasicInfoDO getMicrobeBasicInfo(Long id) {
        return microbeBasicInfoMapper.selectById(id);
    }

    @Override
    public PageResult<MicrobeBasicInfoRespVO> getMicrobeBasicInfoPage(MicrobeBasicInfoPageReqVO pageReqVO) {
        PageResult<MicrobeBasicInfoDO> microbeBasicInfoDOPageResult = microbeBasicInfoMapper.selectPage(pageReqVO);
        PageResult<MicrobeBasicInfoRespVO> result = BeanUtils.toBean(microbeBasicInfoDOPageResult, MicrobeBasicInfoRespVO.class);

        //获取培养基名称
        List<MicrobeBasicInfoRespVO> list = result.getList();

        Set<Long> mediumIds = list.stream().map(MicrobeBasicInfoRespVO::getMediumId).collect(Collectors.toSet());

        Map<Long, String> mediumMap = cultureMediumDataInfoMapper.selectMediumNameByIds(mediumIds);

        for (MicrobeBasicInfoRespVO microbeBasicInfoRespVO : list) {
            microbeBasicInfoRespVO.setMediumName(mediumMap.get(microbeBasicInfoRespVO.getMediumId()));
        }

        result.setList(list);

        return result;
    }

}