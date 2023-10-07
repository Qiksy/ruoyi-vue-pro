package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;

/**
 * 培养基数据信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CultureMediumDataInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11875")
    private Long id;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "编码不能为空")
    private String code;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "灭菌条件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "灭菌条件不能为空")
    private String sterilizationConditions;

    @Schema(description = "用途", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用途不能为空")
    private String purpose;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类不能为空")
    private String category;

    @Schema(description = "配方", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "配方不能为空")
    private String formula;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}
