package com.cloud.licensingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/4 11:35
 */
@Component
public class ServiceConfig {
    @Value("${example.property}")
    private String exampleProperty;
    @Value(("${redis.server}"))
    private String redisServer;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${kafka.server}")
    private String kafkaServer;

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getRedisServer() {
        return redisServer;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public String getKafkaServer() {
        return kafkaServer;
    }

}

