package cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 销量预警 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeclineWarningRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "23629")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "战区编码")
    @ExcelProperty("战区编码")
    private String zoneCode;

    @Schema(description = "战区名称", example = "李四")
    @ExcelProperty("战区名称")
    private String zoneName;

    @Schema(description = "大区编码", example = "赵六")
    @ExcelProperty("大区编码")
    private String areaName;

    @Schema(description = "大区名称")
    @ExcelProperty("大区名称")
    private String areaCode;

    @Schema(description = "总结")
    @ExcelProperty("总结")
    private String summarize;

    @Schema(description = "附件id", example = "6808")
    @ExcelProperty("附件id")
    private String fileId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}