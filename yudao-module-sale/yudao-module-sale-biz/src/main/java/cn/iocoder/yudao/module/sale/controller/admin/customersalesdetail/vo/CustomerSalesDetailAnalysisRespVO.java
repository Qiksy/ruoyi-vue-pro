package cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 销量下降分析VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerSalesDetailAnalysisRespVO {


    @Schema(description = "客户名称", example = "芋艿")
    @ExcelProperty("客户名称")
    private String customerName;

    @Schema(description = "客户编码")
    @ExcelProperty("客户编码")
    private String customerCode;

    @Schema(description = "科普员", example = "李四")
    @ExcelProperty("科普员")
    private String employeeName;

    private String employeeCode;

    /**
     * 战区编码
     */
    @Schema(description = "战区编码")
    @ExcelProperty("战区编码")
    private String zoneCode;
    /**
     * 战区名称
     */
    @Schema(description = "战区名称")
    @ExcelProperty("战区名称")
    private String zoneName;
    /**
     * 大区编码
     */
    @Schema(description = "大区编码")
    @ExcelProperty("大区编码")
    private String areaName;
    /**
     * 大区名称
     */
    @Schema(description = "大区名称")
    @ExcelProperty("大区名称")
    private String areaCode;
    /**
     * 营盘编码
     */
    @Schema(description = "营盘编码")
    @ExcelProperty("营盘编码")
    private String deptCode;
    /**
     * 营盘名称
     */
    @Schema(description = "营盘名称")
    @ExcelProperty("营盘名称")
    private String deptName;

    @Schema(description = "上月销量")
    @ExcelProperty("上月销量")
    private Double preMonthSales;

    @Schema(description = "当前月份销量")
    @ExcelProperty("当前月份销量")
    private Double currMonthSales;

    @Schema(description = "掉量比例")
    @ExcelProperty("掉量比例")
    private Double declineRatio;

    @Schema(description = "下降数量")
    @ExcelProperty("下降数量")
    private Double declineNum;

}
