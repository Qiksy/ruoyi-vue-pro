package cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 培养基数据信息 DO
 *
 * @author 芋道源码
 */
@TableName("strain_culture_medium_data_info")
@KeySequence("strain_culture_medium_data_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CultureMediumDataInfoDO extends BaseDO {

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
     * 灭菌条件
     */
    private String sterilizationConditions;
    /**
     * 用途
     */
    private String purpose;
    /**
     * 分类
     */
    private String category;
    /**
     * 配方
     */
    private String formula;
    /**
     * 备注
     */
    private String remark;

}
