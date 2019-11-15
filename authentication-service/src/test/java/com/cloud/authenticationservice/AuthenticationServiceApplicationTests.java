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
@MapperScan("com.cloud.authenticationservice.dao")
public class AuthenticationServiceApplicationTests {
	@Resource
	private UserInfoDao userInfoDao;

	@Test
	public void contextLoads() {
		MyUser myUser = userInfoDao.queryUserByUsername("john.carnell");
		System.out.println(myUser);
	}

}
