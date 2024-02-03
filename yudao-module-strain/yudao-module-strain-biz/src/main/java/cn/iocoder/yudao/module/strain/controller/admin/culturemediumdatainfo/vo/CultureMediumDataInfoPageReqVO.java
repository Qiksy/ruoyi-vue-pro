package cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 培养基数据信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CultureMediumDataInfoPageReqVO extends PageParam {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称", example = "芋艿")
    private String name;

}
