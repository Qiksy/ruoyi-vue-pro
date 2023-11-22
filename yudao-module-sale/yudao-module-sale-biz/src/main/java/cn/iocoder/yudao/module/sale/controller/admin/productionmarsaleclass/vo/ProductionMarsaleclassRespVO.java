package cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 销售分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductionMarsaleclassRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22510")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "上级分类", example = "6735")
    @ExcelProperty("上级分类")
    private Long parentId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}