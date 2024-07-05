package cn.iocoder.yudao.module.system.api.tencent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 *
 * 企业微信登录后的响应信息
 * @author linr
 * @since 2024/6/27 下午2:16
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxworkSessionRespDTO {

    /*
    //正常返回的JSON数据包
    {
          "corpid": "CORPID",
          "userid": "USERID",
          "session_key": "kJtdi6RF+Dv67QkbLlPGjw==",
          "errcode": 0,
          "errmsg": "ok"
    }

    //错误时返回JSON数据包(示例为Code无效)
    {
        "errcode": 40029,
        "errmsg": "invalid code"
    }
     */

    private String corpid;

    private String userid;

    private String session_key;

    private Integer errcode;

    private String errmsg;

    private String deviceid;

}
