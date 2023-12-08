package cn.iocoder.yudao.module.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tencent.work")
@Data
public class TencentProperties {

    /**
     * 企业id
     */
    private String corpId;

    /**
     * 应用 ID
     */
    private String agentId;

    /**
     * 应用密钥
     */
    private String secret;

}
