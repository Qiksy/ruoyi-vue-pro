package org.jeecg.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("lowCodeConfig")
@ConfigurationProperties(prefix = "lowcode")
@Data
public class LowCodeConfig {

    /**
     * 平台安全模式配置
     */
    private Firewall firewall;
}
