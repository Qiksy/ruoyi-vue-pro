package cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 销量下降预警子表新增/修改 Request VO")
@Data
public class DeclineWarningSubSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27584")
    private Long id;

    @Schema(description = "客户名称", example = "芋艿")
    private String customerName;

    @Schema(description = "客户编码")
    private String customerCode;

    @Schema(description = "科普员", example = "李四")
    private String employeeName;

    @Schema(description = "营盘", example = "赵六")
    private String deptName;

    @Schema(description = "上月销量")
    private Double preMonthSales;

    @Schema(description = "当前月份销量")
    private Double currMonthSales;

    @Schema(description = "掉量比例")
    private Double declineRatio;

    @Schema(description = "下降数量")
    private Double declineNum;

    @Schema(description = "主表id", example = "27000")
    private Long parentId;

}