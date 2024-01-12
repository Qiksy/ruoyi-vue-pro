package cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 菌种信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MicrobeBasicInfoPageReqVO extends PageParam {

    @Schema(description = "初始编码")
    private String originalCode;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "中文名", example = "李四")
    private String chineseName;

    @Schema(description = "拉丁名", example = "赵六")
    private String latinName;

    @Schema(description = "基因登录号")
    private String geneAccessionNumber;

    @Schema(description = "是否致病（0否1是）")
    private Boolean isPathogenic;

    @Schema(description = "是否公开浏览 （0否1是）")
    private Boolean isVisiable;

}