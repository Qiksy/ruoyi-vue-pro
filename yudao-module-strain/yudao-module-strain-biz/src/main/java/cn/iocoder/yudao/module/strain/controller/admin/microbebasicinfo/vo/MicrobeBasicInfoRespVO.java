package cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 菌种信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MicrobeBasicInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32582")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "初始编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("初始编码")
    private String originalCode;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "保存摄氏度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保存摄氏度")
    private Long temperature;

    @Schema(description = "用途")
    @ExcelProperty("用途")
    private String useage;

    @Schema(description = "中文名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("中文名")
    private String chineseName;

    @Schema(description = "拉丁名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("拉丁名")
    private String latinName;

    @Schema(description = "来源")
    @ExcelProperty("来源")
    private String source;

    @Schema(description = "文献")
    @ExcelProperty("文献")
    private String literatrue;

    @Schema(description = "基因登录号")
    @ExcelProperty("基因登录号")
    private String geneAccessionNumber;

    @Schema(description = "菌种类型（使用字典类型）", example = "2")
    @ExcelProperty("菌种类型（使用字典类型）")
    private String microbeType;

    @Schema(description = "菌落形态")
    @ExcelProperty("菌落形态")
    private String colonyMorphology;

//    @Schema(description = "培养基id", example = "10806")
//    @ExcelProperty("培养基id")
    private Long mediumId;

    @Schema(description = "培养基名称")
    @ExcelProperty("培养基名称")
    private String mediumName;

    @Schema(description = "有效期至", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("有效期至")
    private LocalDateTime expirationDate;

    @Schema(description = "保存日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保存日期")
    private LocalDateTime saveDate;

    @Schema(description = "有效期天数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("有效期天数")
    private Integer validityPeriodDays;

    @Schema(description = "是否致病（0否1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否致病（0否1是）", converter = DictConvert.class)
    @DictFormat("strain_yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isPathogenic;

    @Schema(description = "是否公开浏览 （0否1是）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否公开浏览 （0否1是）", converter = DictConvert.class)
    @DictFormat("strain_yes_no") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isVisiable;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;


    //保存方式
    private String storageMode;


    //菌种图片列表
    //形如 [1354985,165765465]
    private String microbeImages;

    //菌种附件说明
    // 形如 [1354985,165765465]
    private String  microbeAttachment;

    //菌体形态
    private String microbialMorphology;
}