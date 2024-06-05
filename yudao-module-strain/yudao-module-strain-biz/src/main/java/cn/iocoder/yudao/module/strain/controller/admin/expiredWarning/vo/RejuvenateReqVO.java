package cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

/**
 * 传代 / 复壮 请求 VO
 * @author linr
 * @since 2024/5/22 上午10:16
 */
@Data
public class RejuvenateReqVO {

    @NotNull
    private Long[] ids;


    private String remark;


    /**
     *
     */
    @NotNull
    private String type;
}
