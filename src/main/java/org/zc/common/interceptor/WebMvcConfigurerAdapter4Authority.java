package org.zc.common.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义拦截器
 * Created by ceek on 2018-04-23 0:27.
 */
@EnableWebMvc
@Configuration
public class WebMvcConfigurerAdapter4Authority extends WebMvcConfigurerAdapter {
    @Bean
    LoginUserInterceptor loginUserInterceptor() {
        return new LoginUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/userLogin");
//                .excludePathPatterns("/seengene/login")
//                .excludePathPatterns("/seengene/logindo");
    }
}
