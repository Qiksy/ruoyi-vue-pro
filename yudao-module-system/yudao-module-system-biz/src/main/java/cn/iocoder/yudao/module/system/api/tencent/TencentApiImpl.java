package cn.iocoder.yudao.module.system.api.tencent;

import cn.iocoder.yudao.module.system.config.TencentProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TencentApiImpl implements TencentApi {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //从配置中读取corpid和corpsecret

    @Resource
    TencentProperties tencentProperties;

    @Override
    public String getAccessToken() throws IOException {
        //从Redis中获取
        String tencentAccessToken = stringRedisTemplate.opsForValue().get("TENCENT_ACCESS_TOKEN");
        if (tencentAccessToken == null) {
            //通过接口去获取新的access_token，然后设置到Redis中

            //新建HTTP请求，调用腾讯接口
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + tencentProperties.getCorpId()
                    + "&corpsecret=" + tencentProperties.getSecret());
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();//获得实体内容
            String content = EntityUtils.toString(entity, "utf-8");//通过实体工具类转换实体输出格式
            System.out.println(content);
            client.close();

            ObjectMapper op = new ObjectMapper();
            JsonNode jsonNode = op.readTree(content);
            String accessToken = jsonNode.get("access_token").asText();
            int expiresIn = jsonNode.get("expires_in").asInt();

            //将获取到的access_token设置到Redis中
            stringRedisTemplate.opsForValue().set("TENCENT_ACCESS_TOKEN",accessToken, expiresIn, TimeUnit.SECONDS);
            return accessToken;
        }
        return tencentAccessToken;
    }

    @Override
    public String resetAccessToken() throws IOException {
        stringRedisTemplate.delete("TENCENT_ACCESS_TOKEN");

        return getAccessToken();
    }


    /**
     * @return 获取jsapi_ticket
     * @throws IOException
     */
    @Override
    public String getJsapiTicket() throws IOException {

        //从Redis中获取
        String tencentJsapiTicket = stringRedisTemplate.opsForValue().get("TENCENT_JSAPI_TICKET");
        if (tencentJsapiTicket != null) {
            return tencentJsapiTicket;
        }

        CloseableHttpClient client = HttpClients.createDefault();

        String accessToken = this.getAccessToken();
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

        return null;
    }
}
