package cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;

/**
 * 冷冻设备层级 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingDeviceHierarchyMapper extends BaseMapperX<FreezingDeviceHierarchyDO> {

    default PageResult<FreezingDeviceHierarchyDO> selectPage(FreezingDeviceHierarchyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingDeviceHierarchyDO>()
                .eqIfPresent(FreezingDeviceHierarchyDO::getParentId, reqVO.getParentId())
                .eqIfPresent(FreezingDeviceHierarchyDO::getFreezingDeviceId, reqVO.getFreezingDeviceId())
                .eqIfPresent(FreezingDeviceHierarchyDO::getIsFinalLevel, reqVO.getIsFinalLevel())
                .eqIfPresent(FreezingDeviceHierarchyDO::getFreezingBoxId, reqVO.getFreezingBoxId())
                .eqIfPresent(FreezingDeviceHierarchyDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FreezingDeviceHierarchyDO::getLayerType, reqVO.getLayerType())
                .orderByDesc(FreezingDeviceHierarchyDO::getId));
    }

    default List<FreezingDeviceHierarchyDO> selectList(FreezingDeviceHierarchyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FreezingDeviceHierarchyDO>()
                .eqIfPresent(FreezingDeviceHierarchyDO::getParentId, reqVO.getParentId())
                .eqIfPresent(FreezingDeviceHierarchyDO::getFreezingDeviceId, reqVO.getFreezingDeviceId())
                .eqIfPresent(FreezingDeviceHierarchyDO::getIsFinalLevel, reqVO.getIsFinalLevel())
                .eqIfPresent(FreezingDeviceHierarchyDO::getFreezingBoxId, reqVO.getFreezingBoxId())
                .eqIfPresent(FreezingDeviceHierarchyDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FreezingDeviceHierarchyDO::getLayerType, reqVO.getLayerType())
                .orderByDesc(FreezingDeviceHierarchyDO::getId));
    }

}
