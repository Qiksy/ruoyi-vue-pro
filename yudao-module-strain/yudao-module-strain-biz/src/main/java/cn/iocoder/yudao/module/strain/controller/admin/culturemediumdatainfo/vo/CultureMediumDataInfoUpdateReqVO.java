package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 培养基数据信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CultureMediumDataInfoUpdateReqVO extends CultureMediumDataInfoBaseVO {

}
