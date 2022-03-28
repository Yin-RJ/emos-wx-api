package com.yinrj.emoswxapi.aop;

import com.yinrj.emoswxapi.common.config.shiro.ThreadLocalToken;
import com.yinrj.emoswxapi.common.response.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/28
 * @description 添加令牌的切面类
 */
@Component
@Aspect
public class TokenAspect {
    @Resource
    private ThreadLocalToken threadLocalToken;

    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * com.yinrj.emoswxapi.controller.*.*(..))")
    public void aspect() {

    }

    /**
     * 环绕方法
     * @param point
     * @return
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Result result = (Result) point.proceed();
        String token = threadLocalToken.getToken();
        if (token != null) {
            // 生成了新的令牌
            result.put("token", token);
            threadLocalToken.clearToken();
        }
        return result;
    }
}
