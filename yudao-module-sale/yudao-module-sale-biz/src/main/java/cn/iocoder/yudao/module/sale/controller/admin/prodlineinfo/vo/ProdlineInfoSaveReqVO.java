package cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 产品线新增/修改 Request VO")
@Data
public class ProdlineInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16139")
    private Long id;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "父级ID", example = "23703")
    private Long parentId;

}