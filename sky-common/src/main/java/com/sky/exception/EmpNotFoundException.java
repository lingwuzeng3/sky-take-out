package com.sky.exception;

/**
 * 包名：com.sky.exception
 * 用户：admin
 * 日期：2025-09-25
 * 项目名称：sky-take-out
 */
//员工未发现异常
public class EmpNotFoundException extends RuntimeException {
    public EmpNotFoundException() {}
    public EmpNotFoundException(String message) {
        super(message);
    }
}
