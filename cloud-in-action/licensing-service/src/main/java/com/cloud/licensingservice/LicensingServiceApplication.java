package com.cloud.licensingservice;

import com.cloud.licensingservice.event.models.OrganizationChangeModel;
import com.cloud.licensingservice.util.UserContextFilter;
import com.cloud.licensingservice.util.UserContextInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
// 使用discovery的服务发现功能，可以查询其他已注册的服务
@EnableDiscoveryClient
// 使用feign的
@EnableFeignClients
// 使用配置通知
@RefreshScope
// 使用断路由
@EnableCircuitBreaker
// 使用oauth2保护服务
@EnableResourceServer
// 告诉服务使用sink接口中定义的通道来监听传入的消息
@EnableBinding(Sink.class)
public class LicensingServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(LicensingServiceApplication.class);

    /**
     * 这个注解告诉spring cloud stream，每次从input通道接收消息，就会执行
     * loggerSink()方法。spring cloud stream会自动把从通道中传出的消息
     * 反序列化为一个OrganizationChangeModel的Java POJO
     * @param orgChange
     */
    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel orgChange) {
        logger.debug("Receive an event for organization id {}", orgChange.getOrganizationId());
    }

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

}
