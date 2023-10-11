package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 存放区域信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageAreaInfoUpdateReqVO extends StorageAreaInfoBaseVO {

}
