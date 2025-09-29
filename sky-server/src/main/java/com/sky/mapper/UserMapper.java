package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 包名：com.sky.mapper
 * 用户：admin
 * 日期：2025-09-29
 * 项目名称：sky-take-out
 */
@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入新用户
     * @param user
     */

    void insert(User user);
}
