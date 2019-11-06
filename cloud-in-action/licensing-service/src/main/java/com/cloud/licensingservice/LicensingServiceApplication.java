package com.cloud.licensingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// 使用discovery的服务发现功能，可以查询其他已注册的服务
@EnableDiscoveryClient
// 使用feign的
@EnableFeignClients
// 使用配置通知
@RefreshScope
// 使用断路由
@EnableCircuitBreaker
public class LicensingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

    // LoadBalanced注解告诉Spring Cloud创建一个支持Ribbon的RestTemplate类。
    // 使用LoadBalanced并不需要discovery和feign的注解。
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
