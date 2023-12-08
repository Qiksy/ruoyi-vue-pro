package cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


@Schema(description = "管理后台 - 工厂竞品管理列表 Request VO")
@Data
@ToString(callSuper = true)
public class ProductionCompeteInfoListReqVO {

    @Schema(description = "物料名称", example = "32665")
    private String productionName;

    @Schema(description = "物料id", example = "32665")
    private Long productionId;

    @Schema(description = "工厂id", example = "3374")
    private Long deptId;

    @Schema(description = "价格", example = "3533")
    private BigDecimal price;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
}
