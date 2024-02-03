package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 冷冻设备信息 Excel 导出 Request VO，参数和 FreezingDeviceInfoPageReqVO 是一致的")
@Data
public class FreezingDeviceInfoExportReqVO {

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
