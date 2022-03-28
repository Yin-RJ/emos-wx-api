package com.yinrj.emoswxapi.common.config.shiro;

import org.springframework.stereotype.Component;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description 用于存放生成的令牌，作为AOP切面和Filter之间的媒介
 */
@Component
public class ThreadLocalToken {
    private ThreadLocal<String> localToken = new ThreadLocal<>();

    public void setToken(String token) {
        localToken.set(token);
    }

    public String getToken() {
        return localToken.get();
    }

    public void clearToken() {
        localToken.remove();
    }
}
