package cn.iocoder.yudao.module.system.api.tencent;


import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.system.api.tenant.dto.WecomeMessageRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WechatSessionRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WxworkSessionRespDTO;
import cn.iocoder.yudao.module.system.config.TencentAuthProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception0;

@Service
@Slf4j
public class TencentMiniProgramAuthApiImpl implements TencentMiniProgramAuthApi {


    @Resource
    TencentAuthProperties tencentAuthProperties;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    // ////////////////////企业微信///////////////////////////////////


    @Override
    @SneakyThrows
    public WxworkSessionRespDTO wxworkCode2Session(String code) {

        String accessToken = getSelf().getAssistantAccessToken();

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session?access_token=" + accessToken + "&js_code=" + code + "&grant_type=authorization_code");
        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();//获取响应的内容
        String content = EntityUtils.toString(entity, "utf-8");//通过实体工具类转换实体输出格式
        client.close();

        ObjectMapper op = new ObjectMapper();
        //将content转为WxworkSessionRespDTO

        WxworkSessionRespDTO respDTO = op.readValue(content, WxworkSessionRespDTO.class);

        if (respDTO.getErrcode() == 42001) {
            //access_token过期，需要重新获取一次
            //清除缓存
            getSelf().clearAssistantAccessToken();
            return getSelf().wxworkCode2Session(code);
        }

        if (respDTO.getErrcode() != 0) {
            throw exception0(500, "codeToSession错误，错误码: {}，错误信息: {}", respDTO.getErrcode(), respDTO.getErrmsg());
        }
        return respDTO;

    }


    @Override
    @SneakyThrows
    public String openId2userId(String openid) {
        //将openId转为一个json 字符串 例如 {"openid": "oDjGHs-1yCnGrRovBj2yHij5JAAA"}
        String json = "{\"openid\": \"" + openid + "\"}";
        String accessToken = getSelf().getAssistantAccessToken();

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=" + accessToken))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(response.body());

        return jsonNode.get("userid").asText();
    }


    /**
     * 获取播恩助手的 AccessToken
     * 一般来说两个小时就过期了
     * 写key，为的是每次都能重新获取
     *
     * @return token
     */
    @SneakyThrows
    @Cacheable(cacheNames = "tencent:assistant:access_token#7200", key = "'tencent'")
    @Override
    public String getAssistantAccessToken() {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + tencentAuthProperties.getAssistant().getCorpId()
                + "&corpsecret=" + tencentAuthProperties.getAssistant().getSecret());
        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();//获取响应的内容
        String content = EntityUtils.toString(entity, "utf-8");//通过实体工具类转换实体输出格式
        log.info("获取到的access_token:{}", content);
        client.close();//关闭

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(content);

        if (jsonNode.get("errcode").asInt() != 0) {
            //清除缓存
            getSelf().clearAssistantAccessToken();
            // 说明获取错误了，抛出异常
            throw exception0(500, "获取播恩助手的 AccessToken 失败，错误码: {}，错误信息: {}", jsonNode.get("errcode").asInt(), jsonNode.get("errmsg").asText());
        }
        return jsonNode.get("access_token").asText();
    }

    @CacheEvict(cacheNames = "tencent:assistant:access_token#7200", key = "'tencent'")
    @Override
    public void clearAssistantAccessToken() {
    }


    ////////////////////////////////////////////微信小程序////////////////////////////////////////


    /**
     * 微信小程序登录接口
     *
     * @param code 临时授权码
     * @return 用户登录信息
     */
    @Override
    @SneakyThrows
    public WechatSessionRespDTO wechatCode2Session(String code) {

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + tencentAuthProperties.getAssistantMp().getAppId() + "&secret=" + tencentAuthProperties.getAssistantMp().getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code");
        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();//获取响应的内容
        String content = EntityUtils.toString(entity, "utf-8");//通过实体工具类转换实体输出格式
        client.close();

        ObjectMapper op = new ObjectMapper();
        WechatSessionRespDTO respDTO = op.readValue(content, WechatSessionRespDTO.class);
        if (respDTO.getErrcode()!=null && respDTO.getErrcode() != 0) {
            throw exception0(500, "codeToSession错误，错误码: {}，错误信息: {}", respDTO.getErrcode(), respDTO.getErrmsg());
        }
        return respDTO;
    }

    /////////// 播恩销售相关的 ////////////

    /**
     * @return 获取jsapi_ticket
     */
    @Override
    @SneakyThrows
    public String getJsapiTicket() {


        //从Redis中获取
        String tencentJsapiTicket = stringRedisTemplate.opsForValue().get("TENCENT_JSAPI_TICKET");
        if (tencentJsapiTicket != null) {
            return tencentJsapiTicket;
        }

        CloseableHttpClient client = HttpClients.createDefault();

        String accessToken = getSelf().getSaleAccessToken();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=" + accessToken;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        String content = EntityUtils.toString(response.getEntity(), "utf-8");//通过实体工具类转换实体输出格式
        client.close();

        HttpEntity entity = response.getEntity();//获得实体内容
        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(content);
        String ticket = jsonNode.get("ticket").asText();
        int expiresIn = jsonNode.get("expires_in").asInt();

        //将获取到的access_token设置到Redis中
        stringRedisTemplate.opsForValue().set("TENCENT_JSAPI_TICKET",ticket, expiresIn, TimeUnit.SECONDS);

        return ticket;
    }

    /**
     * 发送消息给到企业微信，利用企业微信的应用，微销售使用
     *
     * @param json
     * @return
     * @throws IOException
     */
    @Override
    @SneakyThrows
    public WecomeMessageRespDTO sendWelcomeMessage(String json) throws IOException {
        String accessToken = getSelf().getSaleAccessToken();
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(response.body());
        //转为对象
        return op.convertValue(jsonNode, WecomeMessageRespDTO.class);
    }

    @Override
    @SneakyThrows
    @Cacheable(cacheNames = "tencent:sale:access_token#7200", key = "'tencent'")
    public String getSaleAccessToken() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + tencentAuthProperties.getSale().getCorpId()
                + "&corpsecret=" + tencentAuthProperties.getSale().getSecret());
        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();//获取响应的内容
        String content = EntityUtils.toString(entity, "utf-8");//通过实体工具类转换实体输出格式
        log.info("获取到的access_token:{}", content);
        client.close();//关闭

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(content);

        if (jsonNode.get("errcode").asInt() != 0) {
            //清除缓存
            getSelf().clearAssistantAccessToken();
            // 说明获取错误了，抛出异常
            throw exception0(500, "获取微销售的 AccessToken 失败，错误码: {}，错误信息: {}", jsonNode.get("errcode").asInt(), jsonNode.get("errmsg").asText());
        }
        return jsonNode.get("access_token").asText();
    }

    @CacheEvict(cacheNames = "tencent:sale:access_token#7200", key = "'tencent'")
    @Override
    public void clearSaleAccessToken() {
    }



    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private TencentMiniProgramAuthApiImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }
}
