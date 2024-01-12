package cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 冷冻管库存预录入 DO
 *
 * @author 芋道源码
 */
@TableName("strain_freezing_tube_stock_pre_entry")
@KeySequence("strain_freezing_tube_stock_pre_entry_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreezingTubeStockPreEntryDO extends BaseDO {

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
     * 冷冻管类型id
     */
    private Long tubeId;
    /**
     * 冷冻盒id
     */
    private Long boxId;
    /**
     * 相对位置 
     */
    private String tubePosition;
    /**
     * x轴编号
     */
    private String tubePositionX;
    /**
     * y轴编号
     */
    private String tubePositionY;
    /**
     * 代数
     */
    private Integer generationNumber;
    /**
     * 融冻次数
     */
    private Integer thawFreezeCycleCount;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 课题id
     */
    private Long projectId;
    /**
     * 菌种id
     */
    private Long microbeId;
    /**
     * 有效期至
     */
    private LocalDateTime expirationDate;
    /**
     * 保存日期
     */
    private LocalDateTime saveDate;
    /**
     * 保存人id
     */
    private Long saveBy;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否入库
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean status;

}