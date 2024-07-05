package cn.iocoder.yudao.module.system.api.tencent;


import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.system.api.tenant.dto.WecomeMessageRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WechatSessionRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.dto.WxworkSessionRespDTO;
import cn.iocoder.yudao.module.system.config.TencentAuthProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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

    @Resource
    FileApi fileApi;

    private static final int MAX_RETRIES = 3;


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
    public String openId2userId(String openid)  {
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
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = op.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (jsonNode.get("errcode").asInt() != 0) {
            log.error("openId2userId错误，错误码: {}，错误信息: {}", jsonNode.get("errcode").asInt(), jsonNode.get("errmsg").asText());
            throw exception0(500, "openId2userId错误，错误码: {}，错误信息: {}", jsonNode.get("errcode").asInt(), jsonNode.get("errmsg").asText());
        }

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
        if (respDTO.getErrcode() != null && respDTO.getErrcode() != 0) {
            throw exception0(500, "codeToSession错误，错误码: {}，错误信息: {}", respDTO.getErrcode(), respDTO.getErrmsg());
        }
        return respDTO;
    }


    /**
     * 生成小程序二维码，然后保存到文件服务器里面去
     * 生成的二维码是一个图片
     * 方法返回的应是一个图片的url地址
     */
    /**
     * 获取小程序二维码
     * //     * @param scene 参数，最长32个字符，且只能支持数字，大小写英文以及部分特殊字符 应该就是一个id=123456489789123
     * //     * @param page 跳转页面，不能带有参数，参数需要放在scene中
     * //     * @param checkPath 是否校验页面存在？如果校验的话，只能校验已经发布的页面。如果否则可以允许小程序未发布
     * //     * @param envVersion  正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版。
     */
    @Override
    @SneakyThrows
    public String createMiniProgramQrCode(String scene, String page, int retryCount) throws IOException {
        if (retryCount > MAX_RETRIES) {
            throw exception0(500, "生成小程序二维码失败，重试次数超过限制");
        }

        String fileUrl;
//        String base64;

        // 先判断文件里面是否已经有生成好的了
        fileUrl = fileApi.getUrlByPath("temp" + scene + ".png");

        if (StringUtils.isNotEmpty(fileUrl)) {
            return fileUrl;
        }

        Boolean checkPath = false;
        String envVersion = "develop";

        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("scene", scene);  //页面参数
        objectNode.put("page", page);  //页面
//        objectNode.put("check_path", checkPath);  //是否为正式发布后的小程序页面？
//        objectNode.put("env_version", envVersion);  //小程序环境、

        objectNode.put("width",280);

        log.info("生成小程序二维码的参数: {}", objectNode.toString());

        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + getSelf().getMiniProgramAccessToken()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectNode.toString()))
                    .build();

            // 定义一个http返回类型
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
//            // 将返回的二进制内容转为字符串
//            String responseBody = new String(response.body(), StandardCharsets.UTF_8);
//            log.info("生成小程序二维码的返回: {}", responseBody);
            // 将返回的内容转为对象
//            WxacodeRespVO wxacodeRespVO = new ObjectMapper().readValue(responseBody, WxacodeRespVO.class);
//            //如果返回的内容中有错误码，说明生成二维码失败
//            if (wxacodeRespVO.getErrcode() == 40001) {
//                getSelf().clearMiniProgramAccessToken();
//                // todo 这里可能递归
//                return getSelf().createMiniProgramQrCode(scene, page, retryCount + 1);
//            } else if (wxacodeRespVO.getErrcode() != null && wxacodeRespVO.getErrcode() != 0) {
//                throw exception0(500, "生成小程序二维码错误，错误码: {}，错误信息: {}", wxacodeRespVO.getErrcode(), wxacodeRespVO.getErrmsg());
//            }

            String responseBody = null;
            try {
                responseBody = new String(response.body(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


// 检查字符串是否是有效的 JSON
            boolean isJson = false;
            JsonNode jsonNode = null;
            if (responseBody != null) {
                try {
                    jsonNode = new ObjectMapper().readTree(responseBody);
                    isJson = true;
                } catch (IOException e) {
                    // Ignore the exception
                }
            }

            if (isJson) {
                // 处理 JSON 响应
                if (jsonNode.get("errcode").asInt() == 40001) {
                    getSelf().clearMiniProgramAccessToken();
                    return getSelf().createMiniProgramQrCode(scene, page, retryCount + 1);
                } else {
                    throw exception0(500, "生成小程序二维码错误，错误码: {}，错误信息: {}", jsonNode.get("errcode").asInt(), jsonNode.get("errmsg").asText());
                }

            } else {
                // 处理二进制文件响应
                // ...
                //上传图片  temp 路径下的文件，会定时删除掉，避免空间占用
                fileUrl = fileApi.createFile(scene + ".png", "temp/" + scene + ".png", response.body(), null);

                //将这个response.body()转为base64字符串
//                base64 = Base64.getEncoder().encodeToString(response.body());
            }
        }


        return fileUrl;
    }


    /**
     * 获取小程序接口调用的access_token
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = "tencent:mini_program:access_token#7200", key = "'tencent'")
    public String getMiniProgramAccessToken() {
        //从Redis中获取
        String tencentMiniProgramAccessToken = stringRedisTemplate.opsForValue().get("TENCENT_MINI_PROGRAM_ACCESS_TOKEN");
        if (tencentMiniProgramAccessToken != null) {
            return tencentMiniProgramAccessToken;
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + tencentAuthProperties.getAssistantMp().getAppId() + "&secret=" + tencentAuthProperties.getAssistantMp().getAppSecret());
        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();//获取响应的内容
        String content = EntityUtils.toString(entity, "utf-8");//通过实体工具类转换实体输出格式
        log.info("获取到的access_token:{}", content);
        client.close();//关闭

        ObjectMapper op = new ObjectMapper();
        JsonNode jsonNode = op.readTree(content);
        String accessToken = jsonNode.get("access_token").asText();
        int expiresIn = jsonNode.get("expires_in").asInt();

        //将获取到的access_token设置到Redis中
        stringRedisTemplate.opsForValue().set("TENCENT_MINI_PROGRAM_ACCESS_TOKEN", accessToken, expiresIn, TimeUnit.SECONDS);

        return accessToken;
    }

    @Override
    @CacheEvict(cacheNames = "tencent:mini_program:access_token")
    public void clearMiniProgramAccessToken() {
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
        stringRedisTemplate.opsForValue().set("TENCENT_JSAPI_TICKET", ticket, expiresIn, TimeUnit.SECONDS);

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
