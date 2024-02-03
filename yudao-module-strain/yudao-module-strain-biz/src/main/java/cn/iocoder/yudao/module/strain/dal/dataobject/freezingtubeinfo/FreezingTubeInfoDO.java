package cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 冷冻管基本信息 DO
 *
 * @author 芋道源码
 */
@TableName("strain_freezing_tube_info")
@KeySequence("strain_freezing_tube_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreezingTubeInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 编号
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 容量
     */
    private Integer capacity;
    /**
     * 容量单位（1ml/cm3 2 L/dm3 3 m3）
     *
     * 枚举 {@link TODO strain_volume_unit 对应的类}
     */
    private String volumeUnit;
    /**
     * 备注
     */
    private String remark;

}
