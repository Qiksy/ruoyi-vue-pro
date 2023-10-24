package cn.iocoder.yudao.module.strain.service.freezingdeviceinfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.freezingdeviceinfo.FreezingDeviceInfoConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo.FreezingDeviceInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻设备信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingDeviceInfoServiceImpl implements FreezingDeviceInfoService {

    @Resource
    private FreezingDeviceInfoMapper freezingDeviceInfoMapper;

    @Override
    public Long createFreezingDeviceInfo(FreezingDeviceInfoCreateReqVO createReqVO) {
        // 插入
        FreezingDeviceInfoDO freezingDeviceInfo = FreezingDeviceInfoConvert.INSTANCE.convert(createReqVO);
        freezingDeviceInfoMapper.insert(freezingDeviceInfo);
        // 返回
        return freezingDeviceInfo.getId();
    }

    @Override
    public void updateFreezingDeviceInfo(FreezingDeviceInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingDeviceInfoExists(updateReqVO.getId());
        // 更新
        FreezingDeviceInfoDO updateObj = FreezingDeviceInfoConvert.INSTANCE.convert(updateReqVO);
        freezingDeviceInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingDeviceInfo(Long id) {
        // 校验存在
        validateFreezingDeviceInfoExists(id);
        // 删除
        freezingDeviceInfoMapper.deleteById(id);
    }

    private void validateFreezingDeviceInfoExists(Long id) {
        if (freezingDeviceInfoMapper.selectById(id) == null) {
            throw exception(FREEZING_DEVICE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FreezingDeviceInfoDO getFreezingDeviceInfo(Long id) {
        return freezingDeviceInfoMapper.selectById(id);
    }

    @Override
    public List<FreezingDeviceInfoDO> getFreezingDeviceInfoList(Collection<Long> ids) {
        return freezingDeviceInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FreezingDeviceInfoDO> getFreezingDeviceInfoPage(FreezingDeviceInfoPageReqVO pageReqVO) {
        return freezingDeviceInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FreezingDeviceInfoDO> getFreezingDeviceInfoList(FreezingDeviceInfoExportReqVO exportReqVO) {
        return freezingDeviceInfoMapper.selectList(exportReqVO);
    }

}
