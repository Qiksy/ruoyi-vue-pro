package cn.iocoder.yudao.module.strain.service.microbebasicinfo;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.specimen.SpecimenInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.culturemediumdatainfo.CultureMediumDataInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.SpecimenInfoMapper;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.SpecimenInfoService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.dal.redis.RedisKeyConstants.STRAIN_MICROBE_INFO;
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


    //查询样品数据
    @Resource
    private SpecimenInfoMapper specimenInfoMapper;


    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper;

    @Resource
    private SpecimenInfoService specimenInfoService;

    @Override
    public Long createMicrobeBasicInfo(MicrobeBasicInfoSaveReqVO createReqVO) {
        // 插入
        MicrobeBasicInfoDO microbeBasicInfo = BeanUtils.toBean(createReqVO, MicrobeBasicInfoDO.class);

        //验证是否编码重复
        validateMicrobeBasicInfoCodeExists(microbeBasicInfo.getCode());

        microbeBasicInfoMapper.insert(microbeBasicInfo);
        // 返回
        return microbeBasicInfo.getId();
    }

    private void validateMicrobeBasicInfoCodeExists(String code) {
        LambdaQueryWrapperX<MicrobeBasicInfoDO> queryWrapper = new LambdaQueryWrapperX<MicrobeBasicInfoDO>()
                .eq(MicrobeBasicInfoDO::getCode, code);
        Long count = microbeBasicInfoMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw exception(MICROBE_BASIC_INFO_CODE_EXISTS);
        }
    }

    @Override
    @CacheEvict(value = STRAIN_MICROBE_INFO, key = "#updateReqVO.id", beforeInvocation = true) //删除缓存
    public void updateMicrobeBasicInfo(MicrobeBasicInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateMicrobeBasicInfoExists(updateReqVO.getId());
        // 更新
        MicrobeBasicInfoDO updateObj = BeanUtils.toBean(updateReqVO, MicrobeBasicInfoDO.class);
        microbeBasicInfoMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = STRAIN_MICROBE_INFO, key = "#id") //删除缓存
    public void deleteMicrobeBasicInfo(Long id) {
        // 校验存在
        validateMicrobeBasicInfoExists(id);

        // 删除之前校验是否存在样品数据，如果有则不允许删除
        validateMicrobeSpecimenExists(id);


        // 删除
        microbeBasicInfoMapper.deleteById(id);
    }

    private void validateMicrobeSpecimenExists(Long id) {
        LambdaQueryWrapperX<SpecimenInfoDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<SpecimenInfoDO>()
                .eq(SpecimenInfoDO::getMicrobeId, id);
        Long count = specimenInfoMapper.selectCount(lambdaQueryWrapperX);
        if (count > 0) {
            throw exception(MICROBE_BASIC_INFO_EXISTS_FREEZING_TUBE_STOCK_PRE_ENTRY);
        }
    }

    private void validateMicrobeBasicInfoExists(Long id) {
        if (microbeBasicInfoMapper.selectById(id) == null) {
            throw exception(MICROBE_BASIC_INFO_NOT_EXISTS);
        }
    }

    @Override
    @Cacheable(value = STRAIN_MICROBE_INFO, key = "#id")
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