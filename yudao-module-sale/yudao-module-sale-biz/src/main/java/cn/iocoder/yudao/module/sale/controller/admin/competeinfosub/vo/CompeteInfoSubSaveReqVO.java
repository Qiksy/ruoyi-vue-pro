package cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 竞品信息子新增/修改 Request VO")
@Data
public class CompeteInfoSubSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13959")
    private Long id;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15295")
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    @Schema(description = "价格变动时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "价格变动时间不能为空")
    private LocalDateTime changeDate;

    @Schema(description = "价格变动", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "价格变动不能为空")
    private BigDecimal priceChanges;

    @Schema(description = "附件列表", example = "https://www.iocoder.cn")
    private String fileUrl;

}