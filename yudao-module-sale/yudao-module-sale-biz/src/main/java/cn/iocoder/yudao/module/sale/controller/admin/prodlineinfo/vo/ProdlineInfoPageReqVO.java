package cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 产品线分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProdlineInfoPageReqVO extends PageParam {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16139")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "父级ID", example = "23703")
    @ExcelProperty("父级ID")
    private Long parentId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}
