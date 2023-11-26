package cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 竞品信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompeteInfoPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;


    @Schema(description = "产品id ")
    private Long productionId;


//    @Schema(description = "竞品范围di")
//    private Long competeId;

}