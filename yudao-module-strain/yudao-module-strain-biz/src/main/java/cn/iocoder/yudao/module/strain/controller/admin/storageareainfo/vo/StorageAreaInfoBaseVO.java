package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 存放区域信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class StorageAreaInfoBaseVO {


    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19465")
    private Long id;


    @Schema(description = "编号")
    private String code;


    @Schema(description = "名称", example = "赵六")
    private String name;


    @Schema(description = "位置信息")
    private String locationInfo;






    @Schema(description = "备注", example = "随便")
    private String remark;


}
