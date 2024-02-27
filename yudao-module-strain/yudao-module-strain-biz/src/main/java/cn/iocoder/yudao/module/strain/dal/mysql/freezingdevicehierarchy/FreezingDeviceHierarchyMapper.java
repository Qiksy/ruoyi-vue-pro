package cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.LevelTempInfo;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 递归查询是否存在子层级
     *
     * @param id
     * @return
     */
    default Long selectCountByPid(Long id) {
        LambdaQueryWrapperX<FreezingDeviceHierarchyDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(FreezingDeviceHierarchyDO::getParentId, id);

        List<FreezingDeviceHierarchyDO> hierarchyDOS = selectList(queryWrapperX);

        //如果为空则返回0
        if (hierarchyDOS == null || hierarchyDOS.isEmpty()) {
            return 0L;
        }

        //不为空的话就一直递归查询
        Long count = (long) hierarchyDOS.size();
        for (FreezingDeviceHierarchyDO hierarchyDO : hierarchyDOS) {
            count += selectCountByPid(hierarchyDO.getId());
        }
        return count;
    }


    @Select("""
            with RECURSIVE  cte as (
                        select id,parent_id,name from strain_freezing_device_hierarchy  where id = #{boxId}
                        union all
                        select  t.id, t.parent_id, CONCAT(t.name, '/', cte.name) from strain_freezing_device_hierarchy  t
                        inner join cte on cte.parent_id = t.id
                        )
                        select * from cte
                        order by id LIMIT 1
            """)
    LevelTempInfo selectLevelNameById(Long boxId);
}
