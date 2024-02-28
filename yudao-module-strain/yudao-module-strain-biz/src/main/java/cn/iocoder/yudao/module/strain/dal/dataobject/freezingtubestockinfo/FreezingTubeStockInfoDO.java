package cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo;

import cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import org.glassfish.jaxb.core.v2.TODO;

/**
 * 冷冻盒槽位 DO
 *
 * @author 芋道源码
 */
@TableName("strain_freezing_tube_stock_info")
@KeySequence("strain_freezing_tube_stock_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreezingTubeStockInfoDO extends BaseDO {

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
     * 冷冻盒id  这里是指层级id
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
     * 状态
     *
     * 枚举 {@link InventoryStatisEnum}
     */
    private String status;
    /**
     * 保存人id
     */
    private Long saveBy;

    /**
     * 保存人姓名
     */
    private String saveByName;

    /**
     * 样品id
     */
    private Long stockPreEntryId;

    /**
     * 预录入编号
     */
    private String stockPreEntryCode;

    /**
     * 备注
     */
    private String remark;

}