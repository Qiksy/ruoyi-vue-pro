package cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 菌种信息新增/修改 Request VO")
@Data
public class MicrobeBasicInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32582")
    private Long id;

    @Schema(description = "初始编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "初始编码不能为空")
    private String originalCode;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码不能为空")
    private String code;

    @Schema(description = "保存摄氏度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保存摄氏度不能为空")
    private Long temperature;

    @Schema(description = "用途")
    private String useage;

    @Schema(description = "中文名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "中文名不能为空")
    private String chineseName;

    @Schema(description = "拉丁名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "拉丁名不能为空")
    private String latinName;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "文献")
    private String literatrue;

    @Schema(description = "基因登录号")
    private String geneAccessionNumber;

    @Schema(description = "菌种类型（使用字典类型）", example = "2")
    private String microbeType;

    @Schema(description = "菌落形态")
    private String colonyMorphology;

    @Schema(description = "培养基id", example = "10806")
    private Long mediumId;

    @Schema(description = "有效期至", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "有效期至不能为空")
    private LocalDateTime expirationDate;

    @Schema(description = "保存日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "保存日期不能为空")
    private LocalDateTime saveDate;

    @Schema(description = "有效期天数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "有效期天数不能为空")
    private Integer validityPeriodDays;

    @Schema(description = "是否致病（0否1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否致病（0否1是）不能为空")
    private Boolean isPathogenic;

    @Schema(description = "是否公开浏览 （0否1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否公开浏览 （0否1是）不能为空")
    private Boolean isVisiable;

    @Schema(description = "备注", example = "你说的对")
    private String remark;


    @Schema(description = "菌种图片")
    private String microbeImages;


    @Schema(description = "保存方式")
    private String storageMode;

}