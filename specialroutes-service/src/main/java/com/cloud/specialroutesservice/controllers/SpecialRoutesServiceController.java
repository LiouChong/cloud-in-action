package com.cloud.specialroutesservice.controllers;


import com.cloud.specialroutesservice.model.AbTestingRoute;
import com.cloud.specialroutesservice.services.AbTestingRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/route/")
public class SpecialRoutesServiceController {

    @Autowired
    AbTestingRouteService routeService;

    @RequestMapping(value="abtesting/{serviceName}",method = RequestMethod.GET)
    public AbTestingRoute abstestings(@PathVariable("serviceName") String serviceName) {

        return routeService.getRoute( serviceName);
    }

}
