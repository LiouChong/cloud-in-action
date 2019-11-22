package com.cloud.cloudzuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/22 16:17
 */
@Component
public class ZuulConfig {
    @Value("${zuul.routes.licensingservice}")
    private String licensingServiceZuul;

    public String getLicensingServiceZuul() {
        return licensingServiceZuul;
    }
}
