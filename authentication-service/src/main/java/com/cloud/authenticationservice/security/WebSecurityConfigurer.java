package com.cloud.authenticationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Author: Liuchong
 * Description: 为应用程序定义用户ID、密码和角色
 * date: 2019/11/15 15:26
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * authenticationManagerBean被Spring Security用来处理验证
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Spring Security使用UserDetailsService处理返回的用户信息，
     * 这些用户信息将由Spring Security返回
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    /**
     * configure方法是定义用户、密码和角色的地方
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john.carnell").password("password1").roles("USER")
                .and()
                .withUser("willian.woodward").password("password2").roles("USER", "ADMIN");
    }
}
