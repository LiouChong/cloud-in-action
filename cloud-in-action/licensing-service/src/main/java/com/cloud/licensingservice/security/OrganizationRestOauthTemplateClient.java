package com.cloud.licensingservice.security;

import org.springframework.stereotype.Component;

/**
 * Author: Liuchong
 * Description: 将oauth token在服务之间传递需要的restTemplate特殊类
 * date: 2019/11/16 0016 下午 2:07
 */
@Component
public class OrganizationRestOauthTemplateClient {
 /*   @Autowired
    @Qualifier("oauthRestTemplate")
    private OAuth2RestTemplate oAuth2RestTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationDiscoverClient.class);

    public Organization getOrganization(String organizationId) {
        logger.debug("In Licensing Service.getOrganization:{}", organizationId);

        ResponseEntity<Organization> restExchange = oAuth2RestTemplate.exchange("http://localhost:5555/api/organization/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId);

        return restExchange.getBody();
    }*/
}
