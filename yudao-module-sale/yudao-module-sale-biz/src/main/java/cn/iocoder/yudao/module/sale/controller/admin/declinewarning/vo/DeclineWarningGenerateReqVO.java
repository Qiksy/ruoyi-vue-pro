package cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeclineWarningGenerateReqVO {

    /**
     * 0 本月十号
     * 1 本月20号
     * 2 本月最后一天
     * 4 其他月份的
     */
    @NotNull
    private Integer type;
    private String date;
}
