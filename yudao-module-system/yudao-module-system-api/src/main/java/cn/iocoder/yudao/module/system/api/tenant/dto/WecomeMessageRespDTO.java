package cn.iocoder.yudao.module.system.api.tenant.dto;

import lombok.Data;

/**
 * 响应实例
 *
 * @author linr
 * @since 2023/12/4 2:53
 */
@Data
public class WecomeMessageRespDTO {

    /**
     * 返回码
     */
    private Integer errcode;

    /**
     * 对返回码的文本描述内容
     */
    private String errmsg;

    /**
     * 无效的用户id
     */
    private String invaliduser;

    /**
     * 无效的部门id
     */
    private String invalidparty;

    /**
     * 无效的标签id
     */
    private String invalidtag;

    /**
     * 没有基础接口许可(包含已过期)的userid
     */

    private String unlicenseduser;

    /**
     * 消息id
     */
    private String msgid;

    private String response_code;
}
