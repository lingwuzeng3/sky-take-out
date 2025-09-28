package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 包名：com.sky.mapper
 * 用户：admin
 * 日期：2025-09-27
 * 项目名称：sky-take-out
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据单个菜品id删除口味表记录
     * @param dishId
     */
    @Delete("delete from dish_flavor where id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id集合删除口味表记录
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * 根据菜品id查询口味数据
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
