package com.sky.aspect;

/**
 * 包名：com.sky.aspect
 * 用户：admin
 * 日期：2025-09-25
 * 项目名称：sky-take-out
 */

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段的自动填充
 */
@Aspect
@Component
@Slf4j
//切面 = 切入点 + 通知
public class AutoFillAspect {

    //切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..))  && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    //通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws Exception {
        System.out.println("before autoFill");
        log.info("开始进行公共字段自动填充...");

        //获取到当前拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();//数据库操作类型

        //获取到当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }

        Object entity = args[0];
        Class clazz = entity.getClass();
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //通过反射赋值
        //更新赋值
        Method setUpdateTime = clazz.getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
        Method setUpdateUser = clazz.getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

        setUpdateTime.invoke(entity,now);
        setUpdateUser.invoke(entity,currentId);
        //插入赋值多执行两步
        if(operationType == OperationType.INSERT){

            Method setCreateTime = clazz.getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
            Method setCreateUser = clazz.getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);

            setCreateTime.invoke(entity,now);
            setCreateUser.invoke(entity,currentId);
        }
    }
}
