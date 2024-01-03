package cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 物料信息简单 Response VO")

public class ProductionInfoSimpleRespVO {

    @Schema(description = "物料名称",  example = "乳猪料")
    private String text;

    @Schema(description = "物料id",  example = "1")
    private Long value;

    @Schema(description = "规格")
    private BigDecimal spec;
}
