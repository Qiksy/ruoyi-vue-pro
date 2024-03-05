package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 培养基数据信息创建 Request VO")
@Data
@ToString(callSuper = true)
public class CultureMediumDataInfoCreateReqVO {

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

    @Schema(description = "创建者")
    private String createBy;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    private String updateBy;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime updateTime;

}
