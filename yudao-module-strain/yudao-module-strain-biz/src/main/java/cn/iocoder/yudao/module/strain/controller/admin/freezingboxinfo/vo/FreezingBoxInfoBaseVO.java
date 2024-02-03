package cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 冷冻盒信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FreezingBoxInfoBaseVO {

    @Schema(description = "主键", example = "1024")
    private Long id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "x轴容量")
    private Integer axisCapacityX;

    @Schema(description = "y轴容量")
    private Integer axisCapacityY;

    @Schema(description = "x轴编号类型(0 数字 1 字母)", example = "1")
    private String axisCodeTypeX;

    @Schema(description = "y轴编号类型(0 数字 1 字母)", example = "2")
    private String axisCodeTypeY;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
