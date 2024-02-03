package cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 存放区域信息 Excel 导出 Request VO，参数和 StorageAreaInfoPageReqVO 是一致的")
@Data
public class StorageAreaInfoExportReqVO {

    @Schema(description = "编号")
    private String code;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "位置信息")
    private String locationInfo;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注", example = "随便")
    private String remark;

}
