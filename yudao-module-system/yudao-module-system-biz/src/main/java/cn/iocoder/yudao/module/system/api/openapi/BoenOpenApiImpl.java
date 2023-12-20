package cn.iocoder.yudao.module.system.api.openapi;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BoenOpenApiImpl implements BoenOpenApi{

    @Resource
    private RestTemplate restTemplate;

    @Value("${yudao.remote.secret:boen219689120231207}")
    @Getter
    @Setter
    private String secret;

    @Value("${yudao.remote.url}")
    @Getter
    @Setter
    private String remoteUrl;


    @Override
    public <T> T sendRequest(ParameterizedTypeReference<T> typeRef, String url, String method, String[] timeRange) {
        long timestamp = System.currentTimeMillis();
        String accesstoken = getAccessToken(timestamp);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accesstoken);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        //请求方式
        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());

        // params加上时间戳
        UriComponentsBuilder finalUrl = UriComponentsBuilder.fromHttpUrl(remoteUrl + url)
                .queryParam("timestamp", timestamp);
        if (timeRange != null && timeRange.length == 2) {
            finalUrl.queryParam("timeRange[0]", timeRange[0]);
            finalUrl.queryParam("timeRange[1]", timeRange[1]);
        }

        ResponseEntity<T> response = restTemplate.exchange(
                finalUrl.build().toString(),
                httpMethod,
                entity,
                typeRef
        );




        return response.getBody();
    }

    private String getAccessToken(Long timestamp){
        String temp = secret+ timestamp;

        //进行SHA-1加密
        Digester sha1 = new Digester(DigestAlgorithm.SHA1);

        return sha1.digestHex(temp);
    }

    /*
            //写法二：使用 ObjectMapper 将返回的数据转换成 List<CustomerSalesDetailDO> 类型

            //不是很推荐这个，因为这样的写法没有办法添加请求头，还是推荐使用写法一
            ResponseEntity<String> forEntity = restTemplate.getForEntity(remoteUrl, String.class);
            String body = forEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, CustomerSalesDetailDO.class);
            try {
                List<CustomerSalesDetailDO> customerSalesDetailDOS = objectMapper.readValue(body, javaType);
                return customerSalesDetailDOS;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
         */

}
