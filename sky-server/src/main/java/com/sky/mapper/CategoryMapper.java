package com.sky.mapper;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.Result;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 包名：com.sky.mapper
 * 用户：admin
 * 日期：2025-09-25
 * 项目名称：sky-take-out
 */
@Mapper
public interface CategoryMapper {


    /**
     * 新增分类
     * @param category
     */
    @Insert("insert into category (id, type, name, sort, status, create_time, update_time, create_user, update_user)" +
            "values" +
            "(#{id},#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addCategory(Category category);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    List<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Select("select * from category where type = #{type}")
    Result getByType(Integer type);

    /**
     * 根据id删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(String id);

    /**
     * 启用、禁用分类
     * @param status
     */
    @Update("update category set status = #{status} where id = #{id}")
    void startOrStop(String status,Long id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    @Update("update category set name = #{name},sort = #{sort} where id = #{id}")
    void updateCategory(CategoryDTO categoryDTO);
}
