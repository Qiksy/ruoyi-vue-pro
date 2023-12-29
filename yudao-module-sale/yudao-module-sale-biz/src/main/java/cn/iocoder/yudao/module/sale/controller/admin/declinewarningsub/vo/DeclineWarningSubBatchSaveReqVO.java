package cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 单独写一个vo，方便控制校验
 * @author linr
 * @since 2023/12/27 14:26
 */
@Data
@Schema(description = "管理后台 - 销量下降预警子表/批量更新 Request VO")
public class DeclineWarningSubBatchSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27584")
    private Long id;

    @Schema(description = "原因分析",requiredMode = Schema.RequiredMode.REQUIRED)
    private String reasonAnalysis;

    @Schema(description = "改进措施",requiredMode = Schema.RequiredMode.REQUIRED)
    private String improvementMeasure;


    @Schema(description = "科普员反馈", example = "0否1是")
    private String feedback;
}
