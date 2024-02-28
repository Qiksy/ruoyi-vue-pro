package cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 出库申请新增 Request VO")
@Data
public class OutboundApplicationCreateReqVO {

    private Long id;


    /**
     * 单据号
     */
    private String code;


    @Schema(description = "申请人", requiredMode = Schema.RequiredMode.REQUIRED)

    private String applicant;

    @Schema(description = "用途说明")
    @NotNull(message = "用途说明不能为空")
    private String useage;

    @Schema(description = "是否会重新入库 0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否会重新入库 0否 1是不能为空")
    private Boolean isRestocked;

    @Schema(description = "出库类型：1正常出库 2销毁出库", example = "2")
    @NotNull(message = "出库类型不能为空")
    private String type;

    private String approResult;

    //子表
    List<OutboundApplicationSubCreateReqVO> subList;

}
