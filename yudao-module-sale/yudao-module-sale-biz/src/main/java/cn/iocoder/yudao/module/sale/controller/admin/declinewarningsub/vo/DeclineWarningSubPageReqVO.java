package cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 销量下降预警子表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeclineWarningSubPageReqVO extends PageParam {

    @Schema(description = "客户名称", example = "芋艿")
    private String customerName;

    @Schema(description = "客户编码")
    private String customerCode;

    @Schema(description = "科普员", example = "李四")
    private String employeeName;

    @Schema(description = "营盘", example = "赵六")
    private String deptName;

    @Schema(description = "上月销量")
    private Double preMonthSales;

    @Schema(description = "当前月份销量")
    private Double currMonthSales;

    @Schema(description = "掉量比例")
    private Double declineRatio;

    @Schema(description = "下降数量")
    private Double declineNum;

    @Schema(description = "主表id", example = "27000")
    private Long parentId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}