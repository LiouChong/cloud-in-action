package com.cloud.cloudzuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/13 13:33
 */
@Component
public class ZuulConfig {
    @Value("${zuul.routes.licensingservice}")
    private String licensingServiceZuul;

    public String getLicensingServiceZuul() {
        return licensingServiceZuul;
    }

    public void setLicensingServiceZuul(String licensingServiceZuul) {
        this.licensingServiceZuul = licensingServiceZuul;
    }
}
