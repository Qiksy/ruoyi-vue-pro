package cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo;

import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInTimeRangeDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 会议签到 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SignInInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1400")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "封面图片id", example = "7727")
    @ExcelProperty("封面图片id")
    private Long coverPicId;

    @Schema(description = "封面图片URL", example = "https://www.iocoder.cn")
    @ExcelProperty("封面图片URL")
    private String coverPicUrl;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "说明", example = "随便")
    @ExcelProperty("说明")
    private String description;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    private LocalDateTime endDate;

    @Schema(description = "签到时间类型 0全天 1自定义", example = "1")
    @ExcelProperty("签到时间类型 0全天 1自定义")
    private String signInTimeType;

    @Schema(description = "首次签到是否需要填写个人信息 0否1是")
    @ExcelProperty("首次签到是否需要填写个人信息 0否1是")
    private Boolean personInfoNeed;

    @Schema(description = "是否需要连接指定位置才可以 0否1是")
    @ExcelProperty("是否需要连接指定位置才可以 0否1是")
    private Boolean positionNeed;

    @Schema(description = "位置信息 实际json数据")
    @ExcelProperty("位置信息 实际json数据")
    private String positionInfo;

    @Schema(description = "是否必须扫码签到")
    @ExcelProperty("是否必须扫码签到")
    private Boolean scannerNeed;

    @Schema(description = "签到大屏背景图", example = "13363")
    @ExcelProperty("签到大屏背景图")
    private Long bannerId;

    @Schema(description = "签到大屏背景图 url", example = "https://www.iocoder.cn")
    @ExcelProperty("签到大屏背景图 url")
    private String bannerUrl;

    @Schema(description = "logo_id", example = "8630")
    @ExcelProperty("logo_id")
    private Long logoId;

    @Schema(description = "logo 地址", example = "https://www.iocoder.cn")
    @ExcelProperty("logo 地址")
    private String logoUrl;

    @Schema(description = "标题图片", example = "https://www.iocoder.cn")
    @ExcelProperty("标题图片")
    private String titlePicUrl;

    @Schema(description = "标题图片id", example = "3880")
    @ExcelProperty("标题图片id")
    private Long titlePicId;

    @Schema(description = "需要签到多少次(天数*时间范围)", example = "4142")
    @ExcelProperty("需要签到多少次(天数*时间范围)")
    private Integer signTaskCount;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态 0未开始、1进行中、2已结束", example = "1")
    private Integer status;


    @Schema(description = "创建人")
    private String creator;


    @Schema(description = "创建人姓名")
    private String creatorName;

//    现在是否可以签到
    private Boolean canSignIn;

    /**
     * 签到时间段列表
     */
    private List<SignInTimeRangeDO> signInTimeRangeList;

}