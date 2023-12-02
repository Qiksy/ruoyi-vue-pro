package cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo;

import lombok.Data;

import java.util.List;

@Data
public class DeclineWarningSubmitApprovedReqVO {
    // 业务表主键
    private List<Long> ids;

    /**
     * 当前登录的用户
     */
    private Long loginUserId;
}
