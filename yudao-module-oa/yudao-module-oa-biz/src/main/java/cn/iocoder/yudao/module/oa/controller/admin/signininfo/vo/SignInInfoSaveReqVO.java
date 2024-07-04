package cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo;

import cn.iocoder.yudao.framework.jackson.core.databind.LocalDateTimeStringDeserializer;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInRecordDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInTimeRangeDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;

@Schema(description = "管理后台 - 会议签到新增/修改 Request VO")
@Data
public class SignInInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1400")
    private Long id;

    @Schema(description = "封面图片id", example = "7727")
    private Long coverPicId;

    @Schema(description = "封面图片URL", example = "https://www.iocoder.cn")
    private String coverPicUrl;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "说明", example = "随便")
    private String description;

    @Schema(description = "开始时间")
    @JsonDeserialize(using = LocalDateTimeStringDeserializer.class)
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    @JsonDeserialize(using = LocalDateTimeStringDeserializer.class)
    private LocalDateTime endDate;

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

    @Schema(description = "签到记录列表")
    private List<SignInRecordDO> signInRecords;

    @Schema(description = "签到时间范围列表")
    private List<SignInTimeRangeDO> signInTimeRanges;

    @Schema(description = "是否结束")
    private boolean finished;

}