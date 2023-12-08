package cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 物料分类新增/修改 Request VO")
@Data
public class ProductionMarbasclassSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12739")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "父级", example = "3225")
    private Long parentId;

}