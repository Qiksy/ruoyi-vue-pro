package cn.iocoder.yudao.module.sale.service.remote;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.CustomerSalesDetailSyncReqVO;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Getter
@Setter
@Service
@Slf4j
public class RemoteServiceImpl implements RemoteService {


    @Value("${yudao.remote.url}")
    private String remoteUrl;

    @Value("${yudao.remote.secret}")
    private String secret;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 获取客户销售明细
     *
     * @param reqVO
     */
    @Override
    public List<CustomerSalesDetailDO> getCustomerSalesDetail(CustomerSalesDetailSyncReqVO reqVO)  {
        // 使用 restTemplate 调用远程接口

        // 获取当前时间戳
        Long timestamp = System.currentTimeMillis();
        // 获取 accessToken
        String accessToken = getAccessToken(timestamp);

        /*
        写法一：使用ParameterizedTypeReference定义一个返回类型，这种写法可以获取到泛型的类型
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ParameterizedTypeReference<List<CustomerSalesDetailDO>> typeRef = new ParameterizedTypeReference<List<CustomerSalesDetailDO>>() {};

        String baseUrl = remoteUrl + "/server/data/customerSalesDetail";
        String fullUrl =String.format("%s?timestamp=%s&timeRange[0]=%s&timeRange[1]=%s",
                baseUrl,
                timestamp,
                reqVO.getTimeRange()[0],
                reqVO.getTimeRange()[1]);
        ResponseEntity<List<CustomerSalesDetailDO>> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                entity,
                typeRef,
                timestamp,
                reqVO.getTimeRange()[0],
                reqVO.getTimeRange()[1]);



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



        return response.getBody();
    }

    private String getAccessToken(Long timestamp){
        String temp = secret+ timestamp;

        //进行SHA-1加密
        Digester sha1 = new Digester(DigestAlgorithm.SHA1);

        return sha1.digestHex(temp);
    }
}
