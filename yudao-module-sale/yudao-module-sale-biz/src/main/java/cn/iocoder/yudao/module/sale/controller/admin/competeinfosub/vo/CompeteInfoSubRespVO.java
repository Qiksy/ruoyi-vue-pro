package cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 竞品信息子 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CompeteInfoSubRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13959")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15295")
    @ExcelProperty("父级id")
    private Long parentId;

    @Schema(description = "价格变动时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("价格变动时间")
    private LocalDateTime changeDate;

    @Schema(description = "价格变动", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("价格变动")
    private BigDecimal priceChanges;

    @Schema(description = "附件列表", example = "https://www.iocoder.cn")
    @ExcelProperty("附件列表")
    private String fileUrl;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}