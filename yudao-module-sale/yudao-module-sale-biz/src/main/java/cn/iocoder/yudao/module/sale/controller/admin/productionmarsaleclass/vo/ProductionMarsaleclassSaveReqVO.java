package cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 销售分类新增/修改 Request VO")
@Data
public class ProductionMarsaleclassSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22510")
    private Long id;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "上级分类", example = "6735")
    private Long parentId;

}