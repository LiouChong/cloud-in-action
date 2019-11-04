package com.cloud.licensingservice.controller;

import com.cloud.licensingservice.pojo.License;
import com.cloud.licensingservice.pojo.vo.request.BaseRequest;
import com.cloud.licensingservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/organizations")
public class LicenseServiceController {
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
}
