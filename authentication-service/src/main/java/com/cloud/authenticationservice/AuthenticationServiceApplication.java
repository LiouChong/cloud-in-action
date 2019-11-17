package com.cloud.authenticationservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableResourceServer
// 用于告诉spring cloud，该服务将作为Oauth2服务
@EnableAuthorizationServer
@MapperScan("com.cloud.authenticationservice.dao")
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	/**
	 * 此端点由受保护服务调用，以确保Oauth2保护的服务时，将会用到这个端点
	 * @param user
	 * @return
	 */
	@RequestMapping(value = {"/user"}, produces = "application/json")
	public Map<String, Object> user(OAuth2Authentication user) {
		Map<String, Object> userinfo = new HashMap<>();
		userinfo.put("user", user.getUserAuthentication().getPrincipal());
		userinfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
		return userinfo;
	}

}
