package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 培养基数据信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CultureMediumDataInfoRespVO extends CultureMediumDataInfoBaseVO {

    @Schema(description = "创建者")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
