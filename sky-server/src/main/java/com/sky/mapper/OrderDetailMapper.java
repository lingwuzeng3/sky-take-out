package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 包名：com.sky.mapper
 * 用户：admin
 * 日期：2025-10-04
 * 项目名称：sky-take-out
 */

@Mapper
public interface OrderDetailMapper {

    /**
     * 批量插入购物车商品
     * @param orderDetails
     */

    void insertBatch(List<OrderDetail> orderDetails);


    /**
     * 通过订单id查询订单详细
     * @param orderId
     */
    List<OrderDetail> getByOrderId(Long orderId);
}
