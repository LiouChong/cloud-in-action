package com.cloud.licensingservice.controller;

import com.cloud.licensingservice.pojo.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        License license = new License();
        license.setId(licenseId);
        license.setProduceName("Teleco");
        license.setLicenseType("Seat");
        license.setOrganizationId(organizationId);
        return license;
    }
}
