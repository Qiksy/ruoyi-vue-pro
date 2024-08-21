package org.jeecg.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 作用： 给这个模块的所有的请求，都添加上/online/cgform/api/**的路径
 *
 * @author opv2
 * @since 2024/8/21 下午12:42
 */
@Configuration("onlineConfiguration")
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/interceptor/OnlineConfiguration.class */
public class OnlineConfiguration implements WebMvcConfigurer {
    /**
     * 注册拦截器
     */
    @Bean
    public OnlineInterceptor onlineInterceptor() {
        return new OnlineInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(onlineInterceptor()).excludePathPatterns("/*.html", "/html/**", "/js/**", "/css/**", "/images/**").addPathPatterns("/online/cgform/api/**");
    }
}
