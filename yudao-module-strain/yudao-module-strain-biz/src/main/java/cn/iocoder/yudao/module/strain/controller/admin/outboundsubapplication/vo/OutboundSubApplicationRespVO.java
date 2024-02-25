package cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 出库申请子 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OutboundSubApplicationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "18850")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7966")
    @ExcelProperty("主表id")
    private Long parentId;

    @Schema(description = "冷冻管id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2974")
    @ExcelProperty("冷冻管id")
    private Long tubeId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

}