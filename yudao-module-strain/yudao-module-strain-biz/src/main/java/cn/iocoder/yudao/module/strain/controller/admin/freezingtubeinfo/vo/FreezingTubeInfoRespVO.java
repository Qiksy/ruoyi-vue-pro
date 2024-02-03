package cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 冷冻管基本信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FreezingTubeInfoRespVO extends FreezingTubeInfoBaseVO {

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
