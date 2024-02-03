package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 冷冻设备信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FreezingDeviceInfoBaseVO {


    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26226")
    private Long id;


    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "编号不能为空")
    private String name;


    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "名称不能为空")
    private String code;


    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "类型不能为空")
    private String type;


    @Schema(description = "摄氏度")
    @NotNull
    private Long temperature;


    @Schema(description = "存储区域")
    @NotNull
    private Long storageAreaId;




    @Schema(description = "备注", example = "你猜")
    private String remark;


}
