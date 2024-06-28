package cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 会议签到分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SignInInfoPageReqVO extends PageParam {

    @Schema(description = "封面图片id", example = "7727")
    private Long coverPicId;

    @Schema(description = "封面图片URL", example = "https://www.iocoder.cn")
    private String coverPicUrl;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "说明", example = "随便")
    private String description;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startDate;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endDate;

    @Schema(description = "签到时间类型 0全天 1自定义", example = "1")
    private String signInTimeType;

    @Schema(description = "首次签到是否需要填写个人信息 0否1是")
    private Boolean personInfoNeed;

    @Schema(description = "是否需要连接指定位置才可以 0否1是")
    private Boolean positionNeed;

    @Schema(description = "位置信息 实际json数据")
    private String positionInfo;

    @Schema(description = "是否必须扫码签到")
    private Boolean scannerNeed;

    @Schema(description = "签到大屏背景图", example = "13363")
    private Long bannerId;

    @Schema(description = "签到大屏背景图 url", example = "https://www.iocoder.cn")
    private String bannerUrl;

    @Schema(description = "logo_id", example = "8630")
    private Long logoId;

    @Schema(description = "logo 地址", example = "https://www.iocoder.cn")
    private String logoUrl;

    @Schema(description = "标题图片", example = "https://www.iocoder.cn")
    private String titlePicUrl;

    @Schema(description = "标题图片id", example = "3880")
    private Long titlePicId;

    @Schema(description = "需要签到多少次(天数*时间范围)", example = "4142")
    private Integer signTaskCount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}