package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.FreezingDeviceHierarchyRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 冷冻设备信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingDeviceInfoRespVO extends FreezingDeviceInfoBaseVO {

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "层级关系")
    private List<FreezingDeviceHierarchyRespVO> hierarchyList;

}
