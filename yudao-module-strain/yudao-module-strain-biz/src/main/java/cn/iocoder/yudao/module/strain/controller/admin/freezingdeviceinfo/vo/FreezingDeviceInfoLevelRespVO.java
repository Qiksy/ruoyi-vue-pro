package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class FreezingDeviceInfoLevelRespVO {

    /**
     * 这里有可能是冷冻设备id，也有可能是冷冻设备层级id
     */
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10979")
    private Long id;


    @Schema(description = "父级id", example = "1601")
    private Long parentId;

    @Schema(description = "层级或者设备的名称", example = "第几层")
    private String name;

    @Schema(description = "冷冻设备id", example = "18583")
    private Long freezingDeviceId;


    @Schema(description = "是否为末级")
    private Boolean isFinalLevel;

    @Schema(description = "末级类型id", example = "26971")
    private Long freezingBoxId;


    @Schema(description = "层级类型", example = "1")
    private String layerType;


    /**
     * 子成绩
     */
    private List<FreezingDeviceInfoLevelRespVO> children;

}
