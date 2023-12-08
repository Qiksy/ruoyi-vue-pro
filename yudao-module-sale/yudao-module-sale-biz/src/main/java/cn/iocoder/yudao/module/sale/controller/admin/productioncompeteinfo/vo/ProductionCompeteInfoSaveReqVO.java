package cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 工厂竞品管理新增/修改 Request VO")
@Data
public class ProductionCompeteInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29480")
    private Long id;

    @Schema(description = "物料id", example = "32665")
    private Long productionId;

    @Schema(description = "工厂id", example = "3374")
    private Long deptId;

    @Schema(description = "价格", example = "3533")
    private BigDecimal price;

}