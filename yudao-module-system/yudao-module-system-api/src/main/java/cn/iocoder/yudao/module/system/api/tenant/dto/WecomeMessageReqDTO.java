package cn.iocoder.yudao.module.system.api.tenant.dto;

import lombok.Data;

/**
 * 企业微信应用短信请求DTO
 * @author linr
 * @since 2023/12/4 2:44
 */
@Data
public class WecomeMessageReqDTO {

    /*
     * 请求实例如下：
     * {
     *    "touser" : "UserID1|UserID2|UserID3",
     *    "toparty" : "PartyID1|PartyID2",
     *    "totag" : "TagID1 | TagID2",
     *    "msgtype" : "text",
     *    "agentid" : 1,
     *    "text" : {
     *        "content" : "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。"
     *    },
     *    "safe":0,
     *    "enable_id_trans": 0,
     *    "enable_duplicate_check": 0,
     *    "duplicate_check_interval": 1800
     * }
     */

    /**
     * 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
     * 特殊情况：指定为"@all"，则向该企业应用的全部成员发送
     */
    private String touser;
    /**
     * 指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
     * 当touser为"@all"时忽略本参数
     */
    private String toparty;

    /**
     * 指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
     */
    private String totag;

    /**
     * 消息类型，此时固定为：text
     */
    private String msgtype;

    /**
     * 应用id
     */
    private Integer agentid;

    private Text text;

    /**
     * 否
     */
    private int safe;

    /**
     * 否
     */
    private int enable_id_trans;

    /**
     * 否
     */
    private int enable_duplicate_check;

    /**
     * 否
     */
    private int duplicate_check_interval;




    @Data
    public static class Text {
        /**
         * 消息内容，最长不超过2048个字节，超过将截断
         */
        private String content;
    }
}
