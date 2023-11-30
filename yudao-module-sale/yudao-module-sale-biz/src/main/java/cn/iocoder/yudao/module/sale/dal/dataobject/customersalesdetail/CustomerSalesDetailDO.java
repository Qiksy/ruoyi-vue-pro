package cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 客户销售明细 DO
 *
 * @author 播恩超级管理员
 */
@TableName("sale_customer_sales_detail")
@KeySequence("sale_customer_sales_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSalesDetailDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 战区编码
     */
    private String zoneCode;
    /**
     * 战区名称
     */
    private String zoneName;
    /**
     * 大区编码
     */
    private String areaName;
    /**
     * 大区名称
     */
    private String areaCode;
    /**
     * 营盘编码
     */
    private String deptCode;
    /**
     * 营盘名称
     */
    private String deptName;
    /**
     * 科普员主键
     */
    private String employeePk;
    /**
     * 科普员编码
     */
    private String employeeCode;
    /**
     * 科普员名称
     */
    private String employeeName;
    /**
     * 年月
     */
    private String yearMonth;
    /**
     * 年月日
     */
    private String saleDate;
    /**
     * 日销量
     */
    private Double dailySales;
    /**
     * 月累计销量
     */
    private Double monthlyCumulativeSales;

}