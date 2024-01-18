package cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 冷冻盒槽位分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingTubeStockInfoPageReqVO extends PageParam {

    @Schema(description = "编号")
    private String code;

    @Schema(description = "冷冻管类型id", example = "13669")
    private Long tubeId;

    @Schema(description = "冷冻盒id", example = "29519")
    private Long boxId;

    @Schema(description = "相对位置")
    private String tubePosition;

    @Schema(description = "x轴编号")
    private String tubePositionX;

    @Schema(description = "y轴编号")
    private String tubePositionY;

    @Schema(description = "代数")
    private Integer generationNumber;

    @Schema(description = "融冻次数", example = "17812")
    private Integer thawFreezeCycleCount;

    @Schema(description = "部门id", example = "11714")
    private Long deptId;

    @Schema(description = "课题id", example = "20755")
    private Long projectId;

    @Schema(description = "菌种id", example = "19534")
    private Long microbeId;

    @Schema(description = "有效期至")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expirationDate;

    @Schema(description = "保存日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] saveDate;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "保存人id")
    private Long saveBy;

    @Schema(description = "预录入id", example = "21863")
    private Long stockPreEntryId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}