package cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub;

import lombok.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 竞品信息子 DO
 *
 * @author 播恩超级管理员
 */
@TableName("sale_compete_info_sub")
@KeySequence("sale_compete_info_sub_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompeteInfoSubDO extends BaseDO {

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
     * 价格变动时间
     */
    private LocalDateTime changeDate;
    /**
     * 价格变动
     */
    private BigDecimal priceChanges;
    /**
     * 附件列表
     */
    private String fileId;

}