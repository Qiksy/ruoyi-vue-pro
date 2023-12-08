package cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 物料信息 DO
 *
 * @author 芋道源码
 */
@TableName("sale_production_info")
@KeySequence("sale_production_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 物料名称
     */
    private String name;
    /**
     * 销售分类
     */
    private Long marsaleclassId;
    /**
     * 物料分类
     */
    private Long marbasclassId;
    /**
     * 产品线id
     */
    private Long prodlineId;
    /**
     * 规格KG/包
     */
    private BigDecimal spec;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 蛋白%
     */
    private BigDecimal protein;

}