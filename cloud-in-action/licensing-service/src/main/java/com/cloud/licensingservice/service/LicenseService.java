package com.cloud.licensingservice.service;

import com.cloud.licensingservice.config.ServiceConfig;
import com.cloud.licensingservice.license.OrganizationDiscoverClient;
import com.cloud.licensingservice.license.OrganizationFeignClient;
import com.cloud.licensingservice.license.OrganizationRestTemplateClient;
import com.cloud.licensingservice.pojo.License;
import com.cloud.licensingservice.pojo.Organization;
import com.cloud.licensingservice.repository.LicenseRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/4 11:34
 */
@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    ServiceConfig config;
    @Autowired
    OrganizationFeignClient organizationFeignClient;
    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoverClient organizationDiscoveryClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setProductName(license.getProductName() + config.getExamplePropertty());
        return license;
    }

    public void saveLicense(License license) {
        license.setOrganizationId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);
        license.setOrganizationId(org.toString());
        return license;
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;


        switch (clientType) {
            case "feign":
                System.out.println("正在使用feign客户端");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("正在使用rest客户端");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("正在使用服务发现客户端");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
        }
        return organization;
    }
}
