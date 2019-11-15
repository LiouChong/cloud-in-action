package com.cloud.authenticationservice.dao;

import com.cloud.authenticationservice.model.MyUser;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/15 17:19
 */
public interface UserInfoDao {
    MyUser queryUserByUsername(String username);
}
