package cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 销量下降预警子表 DO
 *
 * @author 播恩超级管理员
 */
@TableName("sale_decline_warning_sub")
@KeySequence("sale_decline_warning_sub_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeclineWarningSubDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户编码
     */
    private String customerCode;

    private String employeeCode;
    /**
     * 科普员
     */
    private String employeeName;
    /**
     * 营盘
     */
    private String deptName;
    /**
     * 上月销量
     */
    private Double preMonthSales;
    /**
     * 当前月份销量
     */
    private Double currMonthSales;
    /**
     * 掉量比例
     */
    private Double declineRatio;
    /**
     * 下降数量
     */
    private Double declineNum;
    /**
     * 主表id
     */
    private Long parentId;

    /**
     * 原因分析
     */
    private String reasonAnalysis;
    /**
     * 改进措施
     */
    private String improvementMeasure;
}