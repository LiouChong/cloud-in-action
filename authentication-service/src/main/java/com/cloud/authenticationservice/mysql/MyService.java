package com.cloud.authenticationservice.mysql;

import com.cloud.authenticationservice.dao.UserInfoDao;
import com.cloud.authenticationservice.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/15 17:04
 */
@Configuration

public class MyService /*implements UserDetailsService */{
   /* @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUser user = userInfoDao.queryUserByUsername(s);
        return new User(s, user.getPassword(), user.getRoles());
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
