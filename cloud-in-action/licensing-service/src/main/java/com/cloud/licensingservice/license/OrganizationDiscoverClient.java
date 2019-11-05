package com.cloud.licensingservice.license;

import com.cloud.licensingservice.pojo.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/5 11:14
 */
@Component
public class OrganizationDiscoverClient {
    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        // 获取organization服务的所有实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationService");

        if (instances.size() == 0) {
            return null;
        }

        // 检索要调用的服务端点
        String serviceUrl = String.format("%s/v1/organizations/%s", instances.get(0).getUri().toString(), organizationId);

        // 使用标准的Spring REST模板类去调用服务
        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUrl, HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }

}
