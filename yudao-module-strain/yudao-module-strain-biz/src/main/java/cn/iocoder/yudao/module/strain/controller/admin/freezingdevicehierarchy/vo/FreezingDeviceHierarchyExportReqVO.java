package cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 冷冻设备层级 Excel 导出 Request VO，参数和 FreezingDeviceHierarchyPageReqVO 是一致的")
@Data
public class FreezingDeviceHierarchyExportReqVO {

    @Schema(description = "父级id", example = "1601")
    private Long parentId;

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
