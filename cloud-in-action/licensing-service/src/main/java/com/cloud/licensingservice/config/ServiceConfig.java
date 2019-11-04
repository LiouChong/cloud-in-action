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

    public String getExamplePropertty() {
        return exampleProperty;
    }
}

