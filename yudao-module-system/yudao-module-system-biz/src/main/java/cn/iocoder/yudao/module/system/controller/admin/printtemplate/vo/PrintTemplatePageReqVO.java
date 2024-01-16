package cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 打印模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrintTemplatePageReqVO extends PageParam {

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "编码", example = "芋艿")
    private String code;

    @Schema(description = "模板内容")
    private String templateContent;

    @Schema(description = "系统默认")
    private Boolean isSystemDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}