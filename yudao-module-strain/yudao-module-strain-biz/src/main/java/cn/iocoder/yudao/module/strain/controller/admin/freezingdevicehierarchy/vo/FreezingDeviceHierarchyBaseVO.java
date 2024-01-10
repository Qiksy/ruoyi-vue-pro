package cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 冷冻设备层级 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FreezingDeviceHierarchyBaseVO {


    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10979")
    private Long id;


    @Schema(description = "父级id", example = "1601")
    private Long parentId;

    @Schema(description = "名称", example = "第几层")
    private String name;

    @Schema(description = "冷冻设备id", example = "18583")
    private Long freezingDeviceId;


    @Schema(description = "是否为末级")
    private Boolean isFinalLevel;


    @Schema(description = "末级类型id", example = "26971")
    private Long freezingBoxId;






    @Schema(description = "备注", example = "你说的对")
    private String remark;



    @Schema(description = "层级类型", example = "1")
    private String layerType;

}
