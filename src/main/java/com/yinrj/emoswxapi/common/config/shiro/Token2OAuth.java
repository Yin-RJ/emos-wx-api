package com.yinrj.emoswxapi.common.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description 将jwt令牌包装成shiro需要的认证对象
 */
public class Token2OAuth implements AuthenticationToken {
    private final String token;

    public Token2OAuth(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
