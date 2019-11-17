package com.cloud.authenticationservice.security;

import com.cloud.authenticationservice.mysql.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Author: Liuchong
 * Description: 配置那些用户可以进行授权
 * date: 2019/11/15 15:26
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyService myService;

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
    @Bean(name = "myUserDetailService")
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    /**
     * configure方法是验证用户登录正确性的地方
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myService);
        auth.inMemoryAuthentication().withUser("john.carnell").password("password1").roles("USER")
                .and()
                .withUser("william.woodward").password("password2").roles("USER","ADMIN");
    }
}
