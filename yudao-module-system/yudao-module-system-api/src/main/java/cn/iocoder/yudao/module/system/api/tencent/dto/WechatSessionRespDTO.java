package cn.iocoder.yudao.module.system.api.tencent.dto;

import lombok.Data;

/**
 * 微信小程序 登录返回信息
 * @author linr
 * @since 2024/6/27 下午2:15
 */
@Data
public class WechatSessionRespDTO {
    private String openid;

    private String session_key;

    private String unionid;

    private Integer errcode;

    private String errmsg;
}
