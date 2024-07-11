package org.jeecg.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration("onlineConfiguration")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/interceptor/OnlineConfiguration.class */
public class OnlineConfiguration implements WebMvcConfigurer {
    @Bean
    public OnlineInterceptor onlineInterceptor() {
        return new OnlineInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(onlineInterceptor()).excludePathPatterns("/*.html", "/html/**", "/js/**", "/css/**", "/images/**").addPathPatterns("/online/cgform/api/**");
    }
}
