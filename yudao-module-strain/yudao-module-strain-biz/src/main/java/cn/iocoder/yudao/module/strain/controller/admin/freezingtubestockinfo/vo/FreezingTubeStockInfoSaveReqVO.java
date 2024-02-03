package cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 冷冻盒槽位新增/修改 Request VO")
@Data
public class FreezingTubeStockInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27154")
    private Long id;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "冷冻管类型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13669")
    @NotNull(message = "冷冻管类型id不能为空")
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

    @Schema(description = "有效期至", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "有效期至不能为空")
    private LocalDateTime expirationDate;

    @Schema(description = "保存日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保存日期不能为空")
    private LocalDateTime saveDate;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态不能为空")
    private String status;

    @Schema(description = "保存人id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保存人id不能为空")
    private Long saveBy;

    @Schema(description = "预录入id", example = "21863")
    private Long stockPreEntryId;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}