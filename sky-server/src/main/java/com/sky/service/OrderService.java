package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * 包名：com.sky.service
 * 用户：admin
 * 日期：2025-10-04
 * 项目名称：sky-take-out
 */
public interface OrderService {

    OrderSubmitVO submitOrder(OrdersSubmitDTO orderSubmitDTO);

}
