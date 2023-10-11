package cn.iocoder.yudao.module.strain.service.freezingtubeinfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo.FreezingTubeInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.freezingtubeinfo.FreezingTubeInfoConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubeinfo.FreezingTubeInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻管基本信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingTubeInfoServiceImpl implements FreezingTubeInfoService {

    @Resource
    private FreezingTubeInfoMapper freezingTubeInfoMapper;

    @Override
    public Long createFreezingTubeInfo(FreezingTubeInfoCreateReqVO createReqVO) {
        // 插入
        FreezingTubeInfoDO freezingTubeInfo = FreezingTubeInfoConvert.INSTANCE.convert(createReqVO);
        freezingTubeInfoMapper.insert(freezingTubeInfo);
        // 返回
        return freezingTubeInfo.getId();
    }

    @Override
    public void updateFreezingTubeInfo(FreezingTubeInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingTubeInfoExists(updateReqVO.getId());
        // 更新
        FreezingTubeInfoDO updateObj = FreezingTubeInfoConvert.INSTANCE.convert(updateReqVO);
        freezingTubeInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingTubeInfo(Long id) {
        // 校验存在
        validateFreezingTubeInfoExists(id);
        // 删除
        freezingTubeInfoMapper.deleteById(id);
    }

    private void validateFreezingTubeInfoExists(Long id) {
        if (freezingTubeInfoMapper.selectById(id) == null) {
            throw exception(FREEZING_TUBE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FreezingTubeInfoDO getFreezingTubeInfo(Long id) {
        return freezingTubeInfoMapper.selectById(id);
    }

    @Override
    public List<FreezingTubeInfoDO> getFreezingTubeInfoList(Collection<Long> ids) {
        return freezingTubeInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FreezingTubeInfoDO> getFreezingTubeInfoPage(FreezingTubeInfoPageReqVO pageReqVO) {
        return freezingTubeInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FreezingTubeInfoDO> getFreezingTubeInfoList(FreezingTubeInfoExportReqVO exportReqVO) {
        return freezingTubeInfoMapper.selectList(exportReqVO);
    }

}
