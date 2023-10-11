package cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 冷冻盒信息 Excel 导出 Request VO，参数和 FreezingBoxInfoPageReqVO 是一致的")
@Data
public class FreezingBoxInfoExportReqVO {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
