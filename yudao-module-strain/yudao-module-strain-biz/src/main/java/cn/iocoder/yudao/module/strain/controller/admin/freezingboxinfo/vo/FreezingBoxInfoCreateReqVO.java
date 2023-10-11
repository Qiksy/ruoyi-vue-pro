package cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 冷冻盒信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingBoxInfoCreateReqVO extends FreezingBoxInfoBaseVO {

}
