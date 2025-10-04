package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * 包名：com.sky.service
 * 用户：admin
 * 日期：2025-10-04
 * 项目名称：sky-take-out
 */
public interface ShoppingCartService {

    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void cleanShoppingCart();

    void subOneFromCart(ShoppingCartDTO shoppingCartDTO);
}
