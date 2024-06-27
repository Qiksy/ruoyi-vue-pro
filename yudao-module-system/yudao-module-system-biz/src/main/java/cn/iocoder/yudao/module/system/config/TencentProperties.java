package cn.iocoder.yudao.module.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 企业微信应用，微销售
 * @author linr
 * @since 2024/6/26 下午3:11
 */
@Configuration
@ConfigurationProperties(prefix = "tencent.sale")
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
