package cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 冷冻盒槽位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FreezingTubeStockInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27154")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "编号")
    @ExcelProperty("编号")
    private String code;

    @Schema(description = "冷冻管类型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13669")
    @ExcelProperty("冷冻管类型id")
    private Long tubeId;

    @Schema(description = "冷冻盒id", example = "29519")
    @ExcelProperty("冷冻盒id")
    private Long boxId;

    @Schema(description = "相对位置")
    @ExcelProperty("相对位置")
    private String tubePosition;

    @Schema(description = "x轴编号")
    @ExcelProperty("x轴编号")
    private String tubePositionX;

    @Schema(description = "y轴编号")
    @ExcelProperty("y轴编号")
    private String tubePositionY;

    @Schema(description = "代数")
    @ExcelProperty("代数")
    private Integer generationNumber;

    @Schema(description = "融冻次数", example = "17812")
    @ExcelProperty("融冻次数")
    private Integer thawFreezeCycleCount;

    @Schema(description = "部门id", example = "11714")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "课题id", example = "20755")
    @ExcelProperty("课题id")
    private Long projectId;

    @Schema(description = "菌种id", example = "19534")
    @ExcelProperty("菌种id")
    private Long microbeId;

    /**
     * 菌种名称
     */
    private String microbeName;

    /**
     * 菌种编号
     */
    private String microbeCode;

    /**
     * 菌种类型
     */
    private String microbeType;

    @Schema(description = "有效期至", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("有效期至")
    private LocalDateTime expirationDate;

    @Schema(description = "保存日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保存日期")
    private LocalDateTime saveDate;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("strain_inventory_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String status;

    @Schema(description = "保存人id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保存人id")
    private Long saveBy;

    @Schema(description = "预录入id", example = "21863")
    @ExcelProperty("预录入id")
    private Long stockPreEntryId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

}