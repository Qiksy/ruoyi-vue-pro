package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 出库申请 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OutboundApplicationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19596")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "申请人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("申请人")
    private String applicant;

    @Schema(description = "用途说明")
    @ExcelProperty("用途说明")
    private String useage;

    @Schema(description = "是否会重新入库 0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否会重新入库 0否 1是")
    private Boolean isRestocked;

    @Schema(description = "出库类型：1正常出库 2销毁出库", example = "2")
    @ExcelProperty("出库类型：1正常出库 2销毁出库")
    private String type;

    @Schema(description = "结果反馈")
    @ExcelProperty("结果反馈")
    private String result;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "审批流程实例id", example = "20530")
    @ExcelProperty("审批流程实例id")
    private String processInstanceId;

    @Schema(description = "审批结果")
    @ExcelProperty("审批结果")
    private String approResult;

    /**
     * 子表数据
     */
    private List<OutboundApplicationSubRespVO> subList;


    /**
     * 是否重新入库了
     */
    private boolean allResave;

}