package cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 出库申请子新增/修改 Request VO")
@Data
public class OutboundSubApplicationSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "18850")
    private Long id;

    @Schema(description = "主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7966")
    @NotNull(message = "主表id不能为空")
    private Long parentId;

    @Schema(description = "冷冻管id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2974")
    @NotNull(message = "冷冻管id不能为空")
    private Long tubeId;

    @Schema(description = "备注", example = "随便")
    private String remark;

}