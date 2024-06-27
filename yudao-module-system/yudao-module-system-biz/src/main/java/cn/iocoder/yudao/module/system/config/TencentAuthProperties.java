package cn.iocoder.yudao.module.system.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tencent")
@Data
public class TencentAuthProperties {

    private WxworkAppletProperties sale; //微销售


    private WxworkAppletProperties assistant; // 播恩助手


    private MpProperties assistantMp; // 播恩助手小程序


    /**
     * 企业微信应用的配置
     *
     * @author linr
     * @since 2024/6/27 上午9:28
     */



}
