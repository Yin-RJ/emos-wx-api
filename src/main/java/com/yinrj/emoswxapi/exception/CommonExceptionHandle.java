package com.yinrj.emoswxapi.exception;

import com.yinrj.emoswxapi.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/28
 * @description 统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validException(MethodArgumentNotValidException ex) {
        return Result.error("参数校验失败");
    }

    @ExceptionHandler(EmosException.class)
    public Result validException(EmosException ex) {
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result validException(UnauthorizedException ex) {
        return Result.error("认证失败");
    }


    @ExceptionHandler(Exception.class)
    public Result validException(Exception ex) {
        log.error("发生了异常", ex);
        return Result.error("系统内部错误，请联系管理员！");
    }
}
