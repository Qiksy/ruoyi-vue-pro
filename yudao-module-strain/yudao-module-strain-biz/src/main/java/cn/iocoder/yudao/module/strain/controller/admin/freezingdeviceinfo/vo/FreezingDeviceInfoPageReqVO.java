package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 冷冻设备信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingDeviceInfoPageReqVO extends PageParam {

    @Schema(description = "编号", example = "李四")
    private String name;

    @Schema(description = "名称")
    private String code;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "摄氏度")
    private Long temperature;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
