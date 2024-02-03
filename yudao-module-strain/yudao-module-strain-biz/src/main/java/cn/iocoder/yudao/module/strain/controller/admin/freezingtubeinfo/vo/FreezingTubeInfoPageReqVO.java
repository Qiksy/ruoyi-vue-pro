package cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 冷冻管基本信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingTubeInfoPageReqVO extends PageParam {

    @Schema(description = "编号", example = "123")
    private String code;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "容量", example = "1")
    private Integer capacity;

    @Schema(description = "容量单位（1ml/cm3 2 L/dm3 3 m3）", example = "0")
    private String volumeUnit;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
