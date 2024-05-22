package cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 样品复壮传代记录新增/修改 Request VO")
@Data
public class RegenerationRecordSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11917")
    private Long id;

    @Schema(description = "样品id", example = "543")
    private Long specimenId;

    @Schema(description = "更改内容")
    private String content;

    @Schema(description = "备注", example = "随便")
    private String remark;

}