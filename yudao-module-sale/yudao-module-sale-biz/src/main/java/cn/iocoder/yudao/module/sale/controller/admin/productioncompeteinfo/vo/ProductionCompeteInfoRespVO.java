package cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工厂竞品管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductionCompeteInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29480")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "物料id", example = "32665")
    @ExcelProperty("物料id")
    private Long productionId;

    @Schema(description = "物料名称", example = "物料名称")
    @ExcelProperty("物料名称")
    private String productionName;

    @Schema(description = "工厂id", example = "3374")
    @ExcelProperty("工厂id")
    private Long deptId;

    @Schema(description = "价格", example = "3533")
    @ExcelProperty("价格")
    private BigDecimal price;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}