package cn.iocoder.yudao.module.system.api.tencent;

import cn.iocoder.yudao.module.system.api.tenant.dto.WecomeMessageRespDTO;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * 这里是对接腾讯的接口
 * @author linr
 * @since 2023/11/15 15:29
 */
public interface TencentApi {

    /**
     * 获取access_token
     */
    String getAccessToken() throws IOException;

    String resetAccessToken() throws IOException;

    /**
     * @return 获取jsapi_ticket
     * @throws IOException
     */
    String getJsapiTicket() throws IOException;
    @SneakyThrows
    WecomeMessageRespDTO sendWelcomeMessage(String json) throws IOException;
}
