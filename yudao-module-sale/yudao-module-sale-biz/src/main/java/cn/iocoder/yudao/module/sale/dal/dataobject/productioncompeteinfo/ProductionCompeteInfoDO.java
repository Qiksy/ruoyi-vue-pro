package cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工厂竞品管理 DO
 *
 * @author 芋道源码
 */
@TableName("sale_production_compete_info")
@KeySequence("sale_production_compete_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionCompeteInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 物料id
     */
    private Long productionId;
    /**
     * 工厂id
     */
    private Long deptId;
    /**
     * 价格
     */
    private BigDecimal price;

}