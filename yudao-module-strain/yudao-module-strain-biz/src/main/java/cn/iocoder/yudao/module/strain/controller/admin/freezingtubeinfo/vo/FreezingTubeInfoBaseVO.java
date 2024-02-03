package cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

/**
 * 冷冻管基本信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FreezingTubeInfoBaseVO {

    @Schema(description = "主键", example = "1024")
    private Long id;

    @Schema(description = "编号", example = "123")
    private String code;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "容量", example = "1")
    private Integer capacity;

    @Schema(description = "容量单位（1ml/cm3 2 L/dm3 3 m3）", example = "0")
    private String volumeUnit;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
