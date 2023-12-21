package cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 竞品信息新增/修改 Request VO")
@Data
public class CompeteInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14824")
    private Long id;

    @Schema(description = "品牌名称")
    private String brand;

    @Schema(description = "商品名称", example = "王五")
    private String prodName;

    @Schema(description = "规格KG/包")
    private BigDecimal spec;

    @Schema(description = "对标产品", example = "22867")
    private Long competeId;

    @Schema(description = "初始价格", example = "29687")
    private BigDecimal price;


    private BigDecimal unitPrice;

    private Long productionId;

    /**
     * 我方价格
     */
    private BigDecimal ourPrice;

    private BigDecimal ourUnitPrice;

    private Long deptId;

    private Long areaId;

}