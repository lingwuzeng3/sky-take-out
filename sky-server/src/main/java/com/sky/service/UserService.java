package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * 包名：com.sky.service
 * 用户：admin
 * 日期：2025-09-29
 * 项目名称：sky-take-out
 */
public interface UserService {

    User wxLogin(UserLoginDTO userLoginDTO);
}
