package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 培养基数据信息 Excel 导出 Request VO，参数和 CultureMediumDataInfoPageReqVO 是一致的")
@Data
public class CultureMediumDataInfoExportReqVO {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称", example = "芋艿")
    private String name;

}
