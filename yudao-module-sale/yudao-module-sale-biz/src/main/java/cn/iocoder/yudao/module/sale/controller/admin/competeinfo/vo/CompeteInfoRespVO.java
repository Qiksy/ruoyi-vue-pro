package cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 竞品信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CompeteInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14824")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "品牌名称")
    @ExcelProperty("品牌名称")
    private String brand;

    @Schema(description = "商品名称", example = "王五")
    @ExcelProperty("商品名称")
    private String prodName;

    @Schema(description = "规格KG/包")
    @ExcelProperty("规格KG/包")
    private BigDecimal spec;

    @Schema(description = "对标产品", example = "22867")
    @ExcelProperty("对标产品")
    private Long competeId;

    @Schema(description = "初始价格", example = "29687")
    @ExcelProperty("初始价格")
    private BigDecimal price;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}