package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;

/**
 * 包名：com.sky.service
 * 用户：admin
 * 日期：2025-09-25
 * 项目名称：sky-take-out
 */
public interface CategoryService {

    void addCategory(CategoryDTO categoryDTO);

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    Result getByType(Integer type);

    void deleteById(String id);

    void startOrStop(String status,Long id);

    void updateCategory(CategoryDTO categoryDTO);
}
