package com.cloud.licensingservice.repository;

import com.cloud.licensingservice.pojo.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/4 11:32
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOrOrganizationId(String organizationId);
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
    List<License> findByOrganizationId(String organizationId);
}
