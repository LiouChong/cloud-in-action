package com.cloud.specialroutesservice.services;


import com.cloud.specialroutesservice.exception.NoRouteFound;
import com.cloud.specialroutesservice.model.AbTestingRoute;
import com.cloud.specialroutesservice.repository.AbTestingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbTestingRouteService {
    @Autowired
    private AbTestingRouteRepository abTestingRouteRepository;

    public AbTestingRoute getRoute(String serviceName) {
        AbTestingRoute route = abTestingRouteRepository.findByServiceName(serviceName);

        System.out.println("查询的值为：" + route);
        if (route==null) {
            throw new NoRouteFound();
        }

        return route;
    }

    public void saveAbTestingRoute(AbTestingRoute route){

        abTestingRouteRepository.save(route);

    }

    public void updateRouteAbTestingRoute(AbTestingRoute route){
        abTestingRouteRepository.save(route);
    }

    public void deleteRoute(AbTestingRoute route){
        abTestingRouteRepository.delete(route);
    }
}
