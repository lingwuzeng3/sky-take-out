package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 包名：com.sky.service.impl
 * 用户：admin
 * 日期：2025-09-25
 * 项目名称：sky-take-out
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 新增分类
     * @param categoryDTO
     */
    public void addCategory(CategoryDTO categoryDTO)
    {
        //补充信息
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(1);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        //TODO
        category.setCreateUser(1L);
        category.setUpdateUser(1L);
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.addCategory(category);

    }

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        //得到返回记录的数组，封装为PageInfo
        List<Category> list =  categoryMapper.page(categoryPageQueryDTO);
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public Result getByType(Integer type) {
        return categoryMapper.getByType(type);
    }

    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void deleteById(String id) {
        categoryMapper.deleteById(id);
    }

    /**
     * 启用、禁用分类
     * @param status
     */
    @Override
    public void startOrStop(String status,Long id) {
        categoryMapper.startOrStop(status,id);
    }

    /**
     * 修改分类
     * @param categoryDTO
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        categoryMapper.updateCategory(categoryDTO);
    }
}
