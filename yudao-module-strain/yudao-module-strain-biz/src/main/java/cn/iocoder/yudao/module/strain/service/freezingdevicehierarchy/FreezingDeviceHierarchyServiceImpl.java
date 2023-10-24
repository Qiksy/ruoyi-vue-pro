package cn.iocoder.yudao.module.strain.service.freezingdevicehierarchy;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.freezingdevicehierarchy.FreezingDeviceHierarchyConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻设备层级 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingDeviceHierarchyServiceImpl implements FreezingDeviceHierarchyService {

    @Resource
    private FreezingDeviceHierarchyMapper freezingDeviceHierarchyMapper;

    @Override
    public Long createFreezingDeviceHierarchy(FreezingDeviceHierarchyCreateReqVO createReqVO) {
        // 插入
        FreezingDeviceHierarchyDO freezingDeviceHierarchy = FreezingDeviceHierarchyConvert.INSTANCE.convert(createReqVO);
        freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchy);
        // 返回
        return freezingDeviceHierarchy.getId();
    }

    @Override
    public void updateFreezingDeviceHierarchy(FreezingDeviceHierarchyUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingDeviceHierarchyExists(updateReqVO.getId());
        // 更新
        FreezingDeviceHierarchyDO updateObj = FreezingDeviceHierarchyConvert.INSTANCE.convert(updateReqVO);
        freezingDeviceHierarchyMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingDeviceHierarchy(Long id) {
        // 校验存在
        validateFreezingDeviceHierarchyExists(id);
        // 删除
        freezingDeviceHierarchyMapper.deleteById(id);
    }

    private void validateFreezingDeviceHierarchyExists(Long id) {
        if (freezingDeviceHierarchyMapper.selectById(id) == null) {
            throw exception(FREEZING_DEVICE_HIERARCHY_NOT_EXISTS);
        }
    }

    @Override
    public FreezingDeviceHierarchyDO getFreezingDeviceHierarchy(Long id) {
        return freezingDeviceHierarchyMapper.selectById(id);
    }

    @Override
    public List<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyList(Collection<Long> ids) {
        return freezingDeviceHierarchyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyPage(FreezingDeviceHierarchyPageReqVO pageReqVO) {
        return freezingDeviceHierarchyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyList(FreezingDeviceHierarchyExportReqVO exportReqVO) {
        return freezingDeviceHierarchyMapper.selectList(exportReqVO);
    }

}
