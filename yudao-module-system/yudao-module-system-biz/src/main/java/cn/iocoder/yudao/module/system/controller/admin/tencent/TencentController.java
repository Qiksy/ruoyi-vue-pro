package cn.iocoder.yudao.module.system.controller.admin.tencent;


import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.tencent.TencentApi;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台 - 腾讯相关接口")
@RestController
@RequestMapping("/system/tencent")
public class TencentController {

    @Resource
    TencentApi tencentApi;

    /**
     * 获取一个前面
     *
     * 接收前端传过来的参数 url
     */
    @GetMapping("/getConfigSignature")
    @SneakyThrows
    public CommonResult<JsonNode> getAccessToken(TencentConfigSignatureReqVO reqVO) {
        String jsapiTicket = tencentApi.getJsapiTicket();

        String url = reqVO.getUrl();

        //获取当前的时间戳
        long timestamp = System.currentTimeMillis() / 1000;

        //获取随机字符串
        String noncestr =  DefaultIdentifierGenerator.getInstance().nextId(url).toString();

        String string1 = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s", jsapiTicket, noncestr, timestamp, url);

        //使用sha1加密
        Digester sha1 = new Digester(DigestAlgorithm.SHA1);
        String signature = sha1.digestHex(string1);

        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
        jsonNodes.put("timestamp", timestamp);
        jsonNodes.put("nonceStr", noncestr);
        jsonNodes.put("signature", signature);


        return CommonResult.success(jsonNodes);
    }

    static class TencentConfigSignatureReqVO {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
