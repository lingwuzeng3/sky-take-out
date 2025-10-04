package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 包名：com.sky.controller.admin
 * 用户：admin
 * 日期：2025-09-27
 * 项目名称：sky-take-out
 */

@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(cacheNames="dishCache",key="#dishDTO.categoryId")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    @CacheEvict(cacheNames="dishCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除:{}",ids);
        dishService.deleteBatch(ids);
        return  Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id 指定查询id
     * @return 返回结果
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品:{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 更新修改菜品信息
     * @param dishDTO 传入信息的bean
     * @return Result.success()
     */
    @PutMapping
    @ApiOperation("更新修改菜品信息")
    @CacheEvict(cacheNames="dishCache",allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品信息:{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 菜品起售停售
     * 修改指定菜品的销售状态，并清理相关缓存
     *
     * @param status 菜品状态：1-起售，0-停售
     * @param id 菜品ID
     * @return 操作结果
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    @CacheEvict(cacheNames="dishCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("菜品{}的起售状态为:{}", id, status);
        dishService.startOrStop(status, id);
        return Result.success();
    }
}
