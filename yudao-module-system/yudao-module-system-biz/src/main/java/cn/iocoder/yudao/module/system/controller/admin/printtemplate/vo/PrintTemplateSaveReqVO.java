package cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 打印模板新增/修改 Request VO")
@Data
public class PrintTemplateSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16906")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "编码", example = "001")
    private String code;

    @Schema(description = "模板内容")
    private String templateContent;

    @Schema(description = "系统默认")
    private Boolean isSystemDefault;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}