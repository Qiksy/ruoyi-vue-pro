package cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 冷冻盒信息 DO
 *
 * @author qiksy
 */
@TableName("strain_freezing_box_info")
@KeySequence("strain_freezing_box_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreezingBoxInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * x轴容量
     */
    private Integer axisCapacityX;
    /**
     * y轴容量
     */
    private Integer axisCapacityY;
    /**
     * x轴编号类型(0 数字 1 字母)
     */
    private String axisCodeTypeX;
    /**
     * y轴编号类型(0 数字 1 字母)
     */
    private String axisCodeTypeY;
    /**
     * 备注
     */
    private String remark;


}
