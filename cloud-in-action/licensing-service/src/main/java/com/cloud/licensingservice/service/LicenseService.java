package com.cloud.licensingservice.service;

import com.cloud.licensingservice.config.ServiceConfig;
import com.cloud.licensingservice.pojo.License;
import com.cloud.licensingservice.repository.LicenseRepository;
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

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setProductName(license.getProductName() + config.getExamplePropertty());
        return license;
    }

    public void saveLicense(License license) {
        license.setOrganizationId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }
}
