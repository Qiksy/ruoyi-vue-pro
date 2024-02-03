package cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 销量下降预警子表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeclineWarningSubRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27584")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "客户名称", example = "芋艿")
    @ExcelProperty("客户名称")
    private String customerName;

    @Schema(description = "客户编码")
    @ExcelProperty("客户编码")
    private String customerCode;

    @Schema(description = "科普员", example = "李四")
    @ExcelProperty("科普员")
    private String employeeName;

    @Schema(description = "营盘", example = "赵六")
    @ExcelProperty("营盘")
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

    @Schema(description = "主表id", example = "27000")
    @ExcelProperty("主表id")
    private Long parentId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private String feedback;

    private String feedbackDetail;

}