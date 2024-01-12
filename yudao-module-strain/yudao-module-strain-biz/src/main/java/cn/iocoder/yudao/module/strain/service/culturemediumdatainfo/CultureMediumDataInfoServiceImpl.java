package cn.iocoder.yudao.module.strain.service.culturemediumdatainfo;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo.CultureMediumDataInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.culturemediumdatainfo.CultureMediumDataInfoConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.culturemediumdatainfo.CultureMediumDataInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 培养基数据信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CultureMediumDataInfoServiceImpl implements CultureMediumDataInfoService {

    @Resource
    private CultureMediumDataInfoMapper cultureMediumDataInfoMapper;

    @Override
    public Long createCultureMediumDataInfo(CultureMediumDataInfoCreateReqVO createReqVO) {
        // 插入
        CultureMediumDataInfoDO cultureMediumDataInfo = CultureMediumDataInfoConvert.INSTANCE.convert(createReqVO);
        cultureMediumDataInfoMapper.insert(cultureMediumDataInfo);
        // 返回
        return cultureMediumDataInfo.getId();
    }

    @Override
    public void updateCultureMediumDataInfo(CultureMediumDataInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateCultureMediumDataInfoExists(updateReqVO.getId());
        // 更新
        CultureMediumDataInfoDO updateObj = CultureMediumDataInfoConvert.INSTANCE.convert(updateReqVO);
        cultureMediumDataInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteCultureMediumDataInfo(Long id) {
        // 校验存在
        validateCultureMediumDataInfoExists(id);
        // 删除
        cultureMediumDataInfoMapper.deleteById(id);
    }

    private void validateCultureMediumDataInfoExists(Long id) {
        if (cultureMediumDataInfoMapper.selectById(id) == null) {
            throw exception(CULTURE_MEDIUM_DATA_INFO_NOT_EXISTS);
        }
    }

    @Override
    public CultureMediumDataInfoDO getCultureMediumDataInfo(Long id) {
        return cultureMediumDataInfoMapper.selectById(id);
    }

    @Override
    public List<CultureMediumDataInfoDO> getCultureMediumDataInfoList(Collection<Long> ids) {
        return cultureMediumDataInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CultureMediumDataInfoDO> getCultureMediumDataInfoPage(CultureMediumDataInfoPageReqVO pageReqVO) {
        return cultureMediumDataInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CultureMediumDataInfoDO> getCultureMediumDataInfoList(CultureMediumDataInfoExportReqVO exportReqVO) {
        return cultureMediumDataInfoMapper.selectList(exportReqVO);
    }

    @Override
    public List<CultureMediumDataInfoDO> getSimpleCultureMediumDataInfoList() {

        LambdaQueryWrapperX<CultureMediumDataInfoDO> queryWrapperX = new LambdaQueryWrapperX<>();
        //只查询name和id
        queryWrapperX.select(CultureMediumDataInfoDO::getId,CultureMediumDataInfoDO::getName);

        return cultureMediumDataInfoMapper.selectList(queryWrapperX);
    }
}
