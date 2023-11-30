package cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户销售明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerSalesDetailPageReqVO extends PageParam {

    @Schema(description = "客户编码")
    private String customerCode;

    @Schema(description = "客户名称", example = "张三")
    private String customerName;

    @Schema(description = "战区编码")
    private String zoneCode;

    @Schema(description = "战区名称", example = "芋艿")
    private String zoneName;

    @Schema(description = "大区编码", example = "张三")
    private String areaName;

    @Schema(description = "大区名称")
    private String areaCode;

    @Schema(description = "营盘编码")
    private String deptCode;

    @Schema(description = "营盘名称", example = "赵六")
    private String deptName;

    @Schema(description = "科普员主键")
    private String employeePk;

    @Schema(description = "科普员编码")
    private String employeeCode;

    @Schema(description = "科普员名称", example = "芋艿")
    private String employeeName;

    @Schema(description = "年月")
    private String saleMonth;

    @Schema(description = "年月日")
    private String saleDate;

    @Schema(description = "日销量")
    private Double dailySales;

    @Schema(description = "月累计销量")
    private Double monthlyCumulativeSales;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}