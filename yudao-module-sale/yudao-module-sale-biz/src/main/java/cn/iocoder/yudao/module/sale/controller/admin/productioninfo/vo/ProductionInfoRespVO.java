package cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 物料信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductionInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32051")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "物料名称", example = "赵六")
    @ExcelProperty("物料名称")
    private String name;

    @Schema(description = "销售分类", example = "31220")
    @ExcelProperty("销售分类")
    private Long marsaleclassId;

    @Schema(description = "物料分类", example = "665")
    @ExcelProperty("物料分类")
    private Long marbasclassId;

    @Schema(description = "产品线id", example = "25275")
    @ExcelProperty("产品线id")
    private Long prodlineId;

    @Schema(description = "规格KG/包")
    @ExcelProperty("规格KG/包")
    private BigDecimal spec;

    @Schema(description = "价格", example = "12079")
    @ExcelProperty("价格")
    private BigDecimal price;

    @Schema(description = "蛋白%")
    @ExcelProperty("蛋白%")
    private BigDecimal protein;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}