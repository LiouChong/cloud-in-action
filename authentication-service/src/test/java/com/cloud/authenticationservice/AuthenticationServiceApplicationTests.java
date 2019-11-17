package com.cloud.authenticationservice;


import com.cloud.authenticationservice.dao.UserInfoDao;
import com.cloud.authenticationservice.model.MyUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthenticationServiceApplicationTests {
	@Resource
	private UserInfoDao userInfoDao;

	@Test
	public void contextLoads() {
		String username = "john.carnell";
		MyUser myUser = userInfoDao.queryUserByUsername(username);
		myUser.setRoles(userInfoDao.getUseRole(username));
		System.out.println(myUser);
	}

}
