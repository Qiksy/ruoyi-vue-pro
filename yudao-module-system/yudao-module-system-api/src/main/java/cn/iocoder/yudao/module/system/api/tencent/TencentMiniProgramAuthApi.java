package cn.iocoder.yudao.module.system.api.tencent;

import cn.iocoder.yudao.module.system.api.tenant.dto.WecomeMessageRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WechatSessionRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WxworkSessionRespDTO;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;

import java.io.IOException;

/**
 * 微信小程序 / 企业微信小程序 相关接口
 * @author linr
 * @since 2024/6/26 下午3:54
 */
public interface TencentMiniProgramAuthApi {
    /**
     * 企业微信登录接口
     * 根据code去获取真实的登录信息
     * @param code 临时授权码
     * @return 用户登录信息
     */
    WxworkSessionRespDTO wxworkCode2Session(String code);

    String getAssistantAccessToken();

    /**
     * 清除accessToken
     */
    void clearAssistantAccessToken();

    /**
     * @param openid 微信小程序的openid（用户id）
     * @return 返回企业微信通讯录中，成员的明文userid
     */
    String openId2userId(String openid);




    /////// 小程序中的接口 ///////////

    /**
     * 微信小程序登录接口
     * @param code 临时授权码
     * @return 用户登录信息
     */
    WechatSessionRespDTO wechatCode2Session(String code);


    //////////////// 播恩销售  //////////

    /**
     * @return 获取jsapi_ticket
     */
    String getJsapiTicket();

    String getSaleAccessToken();

    void clearSaleAccessToken();

    /**
     * 发送消息给到企业微信，利用企业微信的应用，微销售使用
     * @param json
     * @return
     * @throws IOException
     */
    @SneakyThrows
    WecomeMessageRespDTO sendWelcomeMessage(String json) throws IOException;
}
