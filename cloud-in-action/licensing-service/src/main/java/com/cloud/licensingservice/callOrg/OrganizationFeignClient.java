package com.cloud.licensingservice.callOrg;

import com.cloud.licensingservice.pojo.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/5 15:39
 */
@FeignClient("organizationService")
public interface OrganizationFeignClient {
    // 定义端点的路径和动作
    @RequestMapping(method = RequestMethod.GET,
    value = "/v1/organizations/{organizationId}",
    consumes = "application/json")
    // 使用PathVariable定义传入端点的参数
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
