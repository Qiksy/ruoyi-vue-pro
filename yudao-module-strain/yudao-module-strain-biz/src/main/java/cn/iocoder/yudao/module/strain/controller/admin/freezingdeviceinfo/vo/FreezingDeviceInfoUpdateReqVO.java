package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 冷冻设备信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingDeviceInfoUpdateReqVO extends FreezingDeviceInfoBaseVO {

}
