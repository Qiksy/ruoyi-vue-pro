package cn.iocoder.yudao.module.strain.service.freezingdevicehierarchy;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 冷冻设备层级 Service 接口
 *
 * @author 芋道源码
 */
public interface FreezingDeviceHierarchyService {

    /**
     * 创建冷冻设备层级
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFreezingDeviceHierarchy(@Valid FreezingDeviceHierarchyCreateReqVO createReqVO);

    /**
     * 更新冷冻设备层级
     *
     * @param updateReqVO 更新信息
     */
    void updateFreezingDeviceHierarchy(@Valid FreezingDeviceHierarchyUpdateReqVO updateReqVO);

    /**
     * 删除冷冻设备层级
     *
     * @param id 编号
     */
    void deleteFreezingDeviceHierarchy(Long id);

    /**
     * 获得冷冻设备层级
     *
     * @param id 编号
     * @return 冷冻设备层级
     */
    FreezingDeviceHierarchyDO getFreezingDeviceHierarchy(Long id);

    /**
     * 获得冷冻设备层级列表
     *
     * @param ids 编号
     * @return 冷冻设备层级列表
     */
    List<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyList(Collection<Long> ids);

    /**
     * 获得冷冻设备层级分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻设备层级分页
     */
    PageResult<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyPage(FreezingDeviceHierarchyPageReqVO pageReqVO);

    /**
     * 获得冷冻设备层级列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 冷冻设备层级列表
     */
    List<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyList(FreezingDeviceHierarchyExportReqVO exportReqVO);

}
