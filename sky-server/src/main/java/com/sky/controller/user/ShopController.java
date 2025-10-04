package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 包名：com.sky.controller.admin
 * 用户：admin
 * 日期：2025-09-28
 * 项目名称：sky-take-out
 */
@RestController("userShopController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "C端-店铺操作接口")
public class ShopController {

    public static final String  SHOP_KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("查询营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_KEY);
        log.info("获取店铺的营业状态为:{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
