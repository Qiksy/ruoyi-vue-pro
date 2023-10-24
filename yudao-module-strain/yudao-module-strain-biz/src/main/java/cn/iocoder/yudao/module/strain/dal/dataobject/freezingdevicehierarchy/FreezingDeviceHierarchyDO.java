package cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 冷冻设备层级 DO
 *
 * @author 芋道源码
 */
@TableName("strain_freezing_device_hierarchy")
@KeySequence("strain_freezing_device_hierarchy_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreezingDeviceHierarchyDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 冷冻设备id
     */
    private Long freezingDeviceId;
    /**
     * 是否为末级
     *
     * 枚举 {@link TODO strain_yes_no 对应的类}
     */
    private Boolean isFinalLevel;
    /**
     * 末级类型id
     */
    private Long freezingBoxId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 层级类型
     */
    private String layerType;

}
