package com.cloud.licensingservice.callOrg;

import com.cloud.licensingservice.pojo.Organization;
import com.cloud.licensingservice.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/26 13:23
 */
@Component
public class OrganizationRedisClient {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationRedisClient.class);

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    private OrganizationRedisRepository redisRepository;

    private Organization checkRedisHasValue(String hashKey) {
        try {
            return redisRepository.findOrganization(hashKey);
        } catch (Exception e) {
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.Exception {}",hashKey, e);
            return null;
        }
    }

    public Organization getOrgValueById(String organizationId) {
        Organization organization = checkRedisHasValue(organizationId);
        if (Objects.isNull(organization)) {
            logger.info("redis缓存中未发现指定值");
        } else {
            logger.info("redis中发现org:{}", organizationId);
            return organization;
        }

        logger.info("调用三方org接口");
        Organization org = organizationRestTemplateClient.getOrganization(organizationId);
        // 添加到缓存
        redisRepository.saveOrganization(org);

        return org;
    }

}
