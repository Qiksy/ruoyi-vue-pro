package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 出库申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OutboundApplicationPageReqVO extends PageParam {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "申请人")
    private String applicant;

    @Schema(description = "用途说明")
    private String useage;

    @Schema(description = "是否会重新入库 0否 1是")
    private Boolean isRestocked;

    @Schema(description = "出库类型：1正常出库 2销毁出库", example = "2")
    private String type;

    @Schema(description = "审批流程实例id", example = "20530")
    private String processInstanceId;

    @Schema(description = "审批结果")
    private String approResult;

}