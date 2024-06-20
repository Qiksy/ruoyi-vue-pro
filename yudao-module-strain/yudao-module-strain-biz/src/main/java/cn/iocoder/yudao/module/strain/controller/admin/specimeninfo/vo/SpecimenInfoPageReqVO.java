package cn.iocoder.yudao.module.strain.controller.admin.specimeninfo.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 样品录入 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SpecimenInfoPageReqVO extends PageParam {

    @Schema(description = "编号")
    private String code;

    @Schema(description = "冷冻管类型id", example = "23902")
    private Long tubeId;

    @Schema(description = "冷冻盒id", example = "24951")
    private Long boxId;

    @Schema(description = "相对位置 ")
    private String tubePosition;

    @Schema(description = "x轴编号")
    private String tubePositionX;

    @Schema(description = "y轴编号")
    private String tubePositionY;

    @Schema(description = "代数")
    private Integer generationNumber;

    @Schema(description = "融冻次数", example = "11998")
    private Integer thawFreezeCycleCount;

    @Schema(description = "部门id", example = "27865")
    private Long deptId;

    @Schema(description = "课题id", example = "10920")
    private Long projectId;

    @Schema(description = "菌种id", example = "15252")
    private Long microbeId;

    @Schema(description = "有效期至")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expirationDate;

    @Schema(description = "保存日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] saveDate;

    @Schema(description = "保存人id")
    private Long saveBy;

    @Schema(description = "保存人姓名")
    private String saveByName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "是否入库", example = "2")
    private String status;

    /**
     * 菌种名称
     */
    @Schema(description = "菌种名称", example = "大肠杆菌")
    private String microbeName;

    private String boxCode;

}