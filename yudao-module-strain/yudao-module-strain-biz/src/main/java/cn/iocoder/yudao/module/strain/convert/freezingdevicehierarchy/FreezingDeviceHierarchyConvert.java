package cn.iocoder.yudao.module.strain.convert.freezingdevicehierarchy;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;

/**
 * 冷冻设备层级 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingDeviceHierarchyConvert {

    FreezingDeviceHierarchyConvert INSTANCE = Mappers.getMapper(FreezingDeviceHierarchyConvert.class);

    FreezingDeviceHierarchyDO convert(FreezingDeviceHierarchyCreateReqVO bean);

    FreezingDeviceHierarchyDO convert(FreezingDeviceHierarchyUpdateReqVO bean);

    FreezingDeviceHierarchyRespVO convert(FreezingDeviceHierarchyDO bean);

    List<FreezingDeviceHierarchyRespVO> convertList(List<FreezingDeviceHierarchyDO> list);

    PageResult<FreezingDeviceHierarchyRespVO> convertPage(PageResult<FreezingDeviceHierarchyDO> page);

    List<FreezingDeviceHierarchyExcelVO> convertList02(List<FreezingDeviceHierarchyDO> list);

}
