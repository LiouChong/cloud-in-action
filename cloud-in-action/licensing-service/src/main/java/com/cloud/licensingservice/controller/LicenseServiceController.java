package com.cloud.licensingservice.controller;

import com.cloud.licensingservice.pojo.License;
import com.cloud.licensingservice.pojo.Organization;
import com.cloud.licensingservice.pojo.vo.request.BaseRequest;
import com.cloud.licensingservice.service.LicenseService;
import com.cloud.licensingservice.util.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/organizations")
public class LicenseServiceController {
    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);

    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "/{organizationId}/licenses/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        License license = new License();
        license.setOrganizationId(organizationId);
        return license;
    }
    @RequestMapping(value = "/getValue", method = RequestMethod.POST)
    public License getLicensesById(@RequestBody BaseRequest baseRequest) {
        return licenseService.getLicense("1", "1");
    }

    @RequestMapping(value="/{organizationId}/{licenseId}/{clientType}",method = RequestMethod.GET)
    public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId,
                                          @PathVariable("clientType") String clientType) {
        return licenseService.getLicense(organizationId,licenseId, clientType);
    }


    @RequestMapping(value = "{organizationId}", method = RequestMethod.GET)
    public List<License> getListLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getListLicenses(organizationId);
    }

    @RequestMapping(value = "{organizationId}/licenses/", method = RequestMethod.GET)
    public List<License> saveLicenses( @PathVariable("organizationId") String organizationId) {
        logger.info("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }

    @RequestMapping(value = "redis/{orgId}/", method = RequestMethod.GET)
    public Organization getOrgWithRedis(@PathVariable("orgId") String orgId) {
        return licenseService.getWithRedis(orgId);
    }
}
