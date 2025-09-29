package com.sky.service.impl;

import ch.qos.logback.core.LogbackException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 包名：com.sky.service.impl
 * 用户：admin
 * 日期：2025-09-29
 * 项目名称：sky-take-out
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String  WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO userLoginDTO) {

        //调用微信接口服务，获取微信用户openid
        String openid = getOpenid(userLoginDTO.getCode());

        //判断openid是否有效
        if(openid==null||openid.equals("")){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断是否新用户
        User user = userMapper.getByOpenid(openid);
        if(user==null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 调用微信接口服务获得openid
     * @param code
     * @return
     */
    private String getOpenid(String code){
        //调用微信接口服务，获取微信用户openid
        Map<String,String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN,map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
