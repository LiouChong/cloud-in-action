package com.cloud.licensingservice.config;

import com.cloud.licensingservice.util.UserContextFilter;
import com.cloud.licensingservice.util.UserContextInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.List;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/22 14:57
 */
@Configuration
public class RestConfig {
    // LoadBalanced注解告诉Spring Cloud创建一个支持Ribbon的RestTemplate类。
    // 使用LoadBalanced并不需要discovery和feign的注解。
    @LoadBalanced
    @Bean("restTemplate")
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        // 将自定义的UserContextInterceptor注入
        if (interceptors == null) {
            restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor());
            restTemplate.setInterceptors(interceptors);
        }
        return restTemplate;
    }


    // 配置调用三方服务的时候将token传递过去
//    @Bean("oauthRestTemplate")
//    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details) {
//        return new OAuth2RestTemplate(details, oAuth2ClientContext);
//    }

    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }
}
