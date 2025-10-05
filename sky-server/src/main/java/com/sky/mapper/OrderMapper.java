package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

/**
 * 包名：com.sky.mapper
 * 用户：admin
 * 日期：2025-10-04
 * 项目名称：sky-take-out
 */

@Mapper
public interface OrderMapper {


    /**
     * 插入订单信息
     * @param orders
     */
    void insert(Orders orders);

}
