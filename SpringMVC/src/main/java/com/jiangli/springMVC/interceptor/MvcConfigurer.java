package com.jiangli.springMVC.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Jiangli
 * @date 2018/12/21 8:57
 */
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(createAdminInterceptor()).addPathPatterns("/admin/**");
    }

    @Bean
    public CrossDomainInterceptor createInterceptor() {
        return new CrossDomainInterceptor();
    }

    @Bean
    public AuthLoginInterceptor createAdminInterceptor() {
        return new AuthLoginInterceptor();
    }
}
