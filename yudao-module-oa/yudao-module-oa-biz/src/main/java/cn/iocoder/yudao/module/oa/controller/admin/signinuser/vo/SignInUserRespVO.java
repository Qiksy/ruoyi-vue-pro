package cn.iocoder.yudao.module.oa.controller.admin.signinuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 会议参与成员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SignInUserRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5675")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "参与成员id", example = "27596")
    @ExcelProperty("参与成员id")
    private Long userId;

    @Schema(description = "会议id", example = "29292")
    @ExcelProperty("会议id")
    private Long meetingId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}