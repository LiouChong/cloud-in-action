package com.thoughtmechanix.organization.controllers;


import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping(value="v1/organizations")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId, HttpServletRequest httpServletRequest) {
        Organization organization = orgService.getOrg(organizationId);
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println(s + "的值为：" + httpServletRequest.getParameter(s));
        }
        return organization;
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization( @PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        orgService.updateOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
       orgService.saveOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("orgId") String orgId,  @RequestBody Organization org) {
        orgService.deleteOrg( org );
    }
}
