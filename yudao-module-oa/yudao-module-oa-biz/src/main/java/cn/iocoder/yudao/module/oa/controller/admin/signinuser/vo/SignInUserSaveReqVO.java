package cn.iocoder.yudao.module.oa.controller.admin.signinuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 会议参与成员新增/修改 Request VO")
@Data
public class SignInUserSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "5675")
    private Long id;

    @Schema(description = "参与成员id", example = "27596")
    private Long userId;

    @Schema(description = "会议id", example = "29292")
    private Long meetingId;

}