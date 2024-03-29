package com.thoughtmechanix.organization.services;

import com.thoughtmechanix.organization.events.source.SimpleSourceBean;
import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    @Transactional(rollbackFor = Exception.class)
    public Organization getOrg(String organizationId) {
        // zuul进行路由的时候，超过1m的调用都会抛出异常。这里测试是否会抛出这个异常
        return orgRepository.findById(organizationId);
    }
    @Transactional(rollbackFor = Exception.class)
    public void saveOrg(Organization org){
        org.setName( UUID.randomUUID().toString());

        orgRepository.save(org);

        // 对服务中修改组织数据的每一个方法，调用SimpleSourceBean.publishOrgChange()
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOrg(Organization org){
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());

    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrg(String id){
        orgRepository.deleteById(id);
        simpleSourceBean.publishOrgChange("DELETE", id);
    }

    public void sendMsg() {
        simpleSourceBean.publishOrgChange("actionTest", "idTest");
    }
}
