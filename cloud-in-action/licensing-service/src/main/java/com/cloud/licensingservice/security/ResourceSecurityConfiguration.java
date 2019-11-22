package com.cloud.licensingservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Author: Liuchong
 * Description: 定义访问该url所需的权限
 * date: 2019/11/16 0016 下午 1:22
 */
@Configuration
public class ResourceSecurityConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            // 该url下的DELETE方法必须有权限ADMIN
            .antMatchers(HttpMethod.DELETE, "/v1/organizations/**")
            // 逗号分离
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated();
    }
}
