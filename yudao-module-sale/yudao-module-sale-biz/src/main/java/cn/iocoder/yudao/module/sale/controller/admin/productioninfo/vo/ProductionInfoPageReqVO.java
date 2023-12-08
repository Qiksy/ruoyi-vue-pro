package cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 物料信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductionInfoPageReqVO extends PageParam {

    @Schema(description = "物料名称", example = "赵六")
    private String name;

    @Schema(description = "销售分类", example = "31220")
    private Long marsaleclassId;

    @Schema(description = "物料分类", example = "665")
    private Long marbasclassId;

    @Schema(description = "产品线id", example = "25275")
    private Long prodlineId;

    @Schema(description = "规格KG/包")
    private BigDecimal spec;

    @Schema(description = "价格", example = "12079")
    private BigDecimal price;

    @Schema(description = "蛋白%")
    private BigDecimal protein;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}