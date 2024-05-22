package cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 样品复壮传代记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RegenerationRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11917")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "样品id", example = "543")
    @ExcelProperty("样品id")
    private Long specimenId;

    @Schema(description = "更改内容")
    @ExcelProperty("更改内容")
    private String content;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

}