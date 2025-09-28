package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 包名：com.sky.mapper
 * 用户：admin
 * 日期：2025-09-28
 * 项目名称：sky-take-out
 */
@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id来查询套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishId(List<Long> dishIds);
}
