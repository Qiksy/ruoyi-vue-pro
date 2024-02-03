package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 存放区域信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageAreaInfoCreateReqVO extends StorageAreaInfoBaseVO {

}
