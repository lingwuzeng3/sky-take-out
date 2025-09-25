package com.sky.annotation;

/**
 * 包名：com.sky.annotation
 * 用户：admin
 * 日期：2025-09-25
 * 项目名称：sky-take-out
 */

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作类型  UPDATE INSERT
    OperationType value();
}
