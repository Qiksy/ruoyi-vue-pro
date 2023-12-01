package cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 销量预警分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeclineWarningPageReqVO extends PageParam {

    @Schema(description = "战区编码")
    private String zoneCode;

    @Schema(description = "战区名称", example = "李四")
    private String zoneName;

    @Schema(description = "大区编码", example = "赵六")
    private String areaName;

    @Schema(description = "大区名称")
    private String areaCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;




}