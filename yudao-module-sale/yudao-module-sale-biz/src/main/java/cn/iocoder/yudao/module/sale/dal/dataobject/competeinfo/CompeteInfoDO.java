package cn.iocoder.yudao.module.sale.dal.dataobject.competeinfo;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 竞品信息 DO
 *
 * @author 芋道源码
 */
@TableName("sale_compete_info")
@KeySequence("sale_compete_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompeteInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 品牌名称
     */
    private String brand;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 规格KG/包
     */
    private BigDecimal spec;
    /**
     * 对标产品（废弃）
     */
    private Long competeId;
    /**
     * 初始价格
     */
    private BigDecimal price;


    /**
     * 初始单价
     */
    private BigDecimal unitPrice;


    /**
     * 对标的我方的产品id
     */
    private Long productionId;


    /**
     * 我方价格（吨价）
     */
    private BigDecimal ourPrice;

    /**
     * 我方价格（单价）
     */
    private BigDecimal ourUnitPrice;


    /**
     * 归属部门（其实这里是选择的几个工厂之一）
     */
    private Long deptId;

}