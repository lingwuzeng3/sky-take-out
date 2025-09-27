package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.result.Result;

/**
 * 包名：com.sky.service
 * 用户：admin
 * 日期：2025-09-27
 * 项目名称：sky-take-out
 */
public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);
}
