package cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 客户销售明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CustomerSalesDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7196")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "客户编码")
    @ExcelProperty("客户编码")
    private String customerCode;

    @Schema(description = "客户名称", example = "张三")
    @ExcelProperty("客户名称")
    private String customerName;

    @Schema(description = "战区编码")
    @ExcelProperty("战区编码")
    private String zoneCode;

    @Schema(description = "战区名称", example = "芋艿")
    @ExcelProperty("战区名称")
    private String zoneName;

    @Schema(description = "大区编码", example = "张三")
    @ExcelProperty("大区编码")
    private String areaName;

    @Schema(description = "大区名称")
    @ExcelProperty("大区名称")
    private String areaCode;

    @Schema(description = "营盘编码")
    @ExcelProperty("营盘编码")
    private String deptCode;

    @Schema(description = "营盘名称", example = "赵六")
    @ExcelProperty("营盘名称")
    private String deptName;

    @Schema(description = "科普员主键")
    @ExcelProperty("科普员主键")
    private String employeePk;

    @Schema(description = "科普员编码")
    @ExcelProperty("科普员编码")
    private String employeeCode;

    @Schema(description = "科普员名称", example = "芋艿")
    @ExcelProperty("科普员名称")
    private String employeeName;

    @Schema(description = "年月")
    @ExcelProperty("年月")
    private String yearMonth;

    @Schema(description = "年月日")
    @ExcelProperty("年月日")
    private String saleDate;

    @Schema(description = "日销量")
    @ExcelProperty("日销量")
    private Double dailySales;

    @Schema(description = "月累计销量")
    @ExcelProperty("月累计销量")
    private Double monthlyCumulativeSales;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}