package cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 销量预警新增/修改 Request VO")
@Data
public class DeclineWarningSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23629")
    private Long id;

    @Schema(description = "战区编码")
    private String zoneCode;

    @Schema(description = "战区名称", example = "李四")
    private String zoneName;

    @Schema(description = "大区编码", example = "赵六")
    private String areaName;

    @Schema(description = "大区名称")
    private String areaCode;

    @Schema(description = "总结")
    private String summarize;

    @Schema(description = "原因分析")
    private String reasonAnalysis;

    @Schema(description = "改进措施")
    private String improvementMeasure;

    @Schema(description = "附件id", example = "6808")
    private String fileId;

    @Schema(description = "对比时间")
    private String competeTime;

    @Schema(description = "流程实例id", example = "8545")
    private Long processInstanceId;

}