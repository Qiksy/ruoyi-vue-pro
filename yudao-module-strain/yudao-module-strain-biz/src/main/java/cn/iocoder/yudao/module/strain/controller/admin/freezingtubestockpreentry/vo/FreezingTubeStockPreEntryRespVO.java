package cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo;

import cn.iocoder.yudao.module.strain.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 冷冻管库存预录入 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FreezingTubeStockPreEntryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4168")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "编号")
    @ExcelProperty("编号")
    private String code;

    @Schema(description = "冷冻管类型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23902")
    @ExcelProperty("冷冻管类型id")
    private Long tubeId;

    private String tubeName;

    @Schema(description = "冷冻盒id", example = "24951")
    @ExcelProperty("冷冻盒id")
    private Long boxId;

    @Schema(description = "相对位置 ")
    @ExcelProperty("相对位置 ")
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

    @Schema(description = "融冻次数", example = "11998")
    @ExcelProperty("融冻次数")
    private Integer thawFreezeCycleCount;

    @Schema(description = "部门id", example = "27865")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "课题id", example = "10920")
    @ExcelProperty("课题id")
    private Long projectId;

    @Schema(description = "菌种id", example = "15252")
    @ExcelProperty("菌种id")
    private Long microbeId;

    @Schema(description = "菌种名称", example = "大肠杆菌")
    @ExcelProperty("菌种名称")
    private String microbeName;

    private String latinName;

    @Schema(description = "有效期至", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("有效期至")
    private LocalDateTime expirationDate;

    @Schema(description = "保存日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保存日期")
    private LocalDateTime saveDate;

//    @Schema(description = "保存人id", requiredMode = Schema.RequiredMode.REQUIRED)
//    @ExcelProperty("保存人id")
    private Long saveBy;

    @Schema(description = "保存人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保存人")
    private String saveByName;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "是否入库", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "是否入库", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.STRAIN_INVENTORY_STATUS) // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String status;



    //对应的槽位id，用来获取位置信息
    private Long stockId;

    //位置信息
    private String positionStr;

}