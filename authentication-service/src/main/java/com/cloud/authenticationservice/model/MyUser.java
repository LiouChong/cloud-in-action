package com.cloud.authenticationservice.model;

import lombok.Data;

import java.util.List;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/15 17:07
 */
@Data
public class MyUser {
    private String userName;

    private String password;

    private List<String> roles;
}
