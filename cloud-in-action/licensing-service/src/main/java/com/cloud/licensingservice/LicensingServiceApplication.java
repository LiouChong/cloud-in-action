package com.cloud.licensingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

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
//@EnableResourceServer
// 告诉服务使用sink接口中定义的通道来监听传入的消息
public class LicensingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

}
