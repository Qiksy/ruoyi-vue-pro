package cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 打印模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrintTemplateRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16906")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "编码", example = "芋艿")
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "模板内容")
    @ExcelProperty("模板内容")
    private String templateContent;

    @Schema(description = "系统默认")
    @ExcelProperty("系统默认")
    private Boolean isSystemDefault;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

}