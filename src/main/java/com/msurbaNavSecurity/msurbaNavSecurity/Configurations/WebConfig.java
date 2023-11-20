package com.msurbaNavSecurity.msurbaNavSecurity.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.msurbaNavSecurity.msurbaNavSecurity.Interceptors.SecurityInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(securityInterceptor).addPathPatterns("/private/**").excludePathPatterns("/api/public/**");
    }

}
