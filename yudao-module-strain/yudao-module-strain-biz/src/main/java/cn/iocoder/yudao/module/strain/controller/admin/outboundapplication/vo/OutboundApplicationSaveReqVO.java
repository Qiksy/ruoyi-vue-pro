package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 出库申请新增/修改 Request VO")
@Data
public class OutboundApplicationSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19596")
    private Long id;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码不能为空")
    private String code;

    @Schema(description = "申请人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "申请人不能为空")
    private String applicant;

    @Schema(description = "用途说明")
    private String useage;

    @Schema(description = "是否会重新入库 0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否会重新入库 0否 1是不能为空")
    private Boolean isRestocked;

    @Schema(description = "出库类型：1正常出库 2销毁出库", example = "2")
    private String type;

    @Schema(description = "结果反馈")
    private String result;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "审批流程实例id", example = "20530")
    private String processInstanceId;

    @Schema(description = "审批结果")
    private String approResult;

}