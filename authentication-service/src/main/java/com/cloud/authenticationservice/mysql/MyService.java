package com.cloud.authenticationservice.mysql;

import com.cloud.authenticationservice.dao.UserInfoDao;
import com.cloud.authenticationservice.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/15 17:04
 */
@Configuration
public class MyService implements UserDetailsService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUser user = userInfoDao.queryUserByUsername(s);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在") ;
        }
        user.setRoles(userInfoDao.getUseRole(s));
        List<GrantedAuthority> roles = new LinkedList<>();
        user.getRoles().forEach(role -> {
            roles.add(new SimpleGrantedAuthority(role));
        });
        return new User(s, user.getPassword(), roles);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
