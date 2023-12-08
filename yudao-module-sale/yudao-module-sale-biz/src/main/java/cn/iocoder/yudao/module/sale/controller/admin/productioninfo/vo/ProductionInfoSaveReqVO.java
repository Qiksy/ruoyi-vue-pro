package cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 物料信息新增/修改 Request VO")
@Data
public class ProductionInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32051")
    private Long id;

    @Schema(description = "物料名称", example = "赵六")
    private String name;

    @Schema(description = "销售分类", example = "31220")
    private Long marsaleclassId;

    @Schema(description = "物料分类", example = "665")
    private Long marbasclassId;

    @Schema(description = "产品线id", example = "25275")
    private Long prodlineId;

    @Schema(description = "规格KG/包")
    private BigDecimal spec;

    @Schema(description = "价格", example = "12079")
    private BigDecimal price;

    @Schema(description = "蛋白%")
    private BigDecimal protein;

}