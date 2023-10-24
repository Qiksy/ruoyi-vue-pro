package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 冷冻设备信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingDeviceInfoCreateReqVO extends FreezingDeviceInfoBaseVO {

}
