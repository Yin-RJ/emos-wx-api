package com.yinrj.emoswxapi.common.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description shiro用来认证和授权
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    /**
     * 判断传入的令牌对象类型
     * @param token 传入的令牌
     * @return 是否是自定义要求的类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    /**
     * 授权，验证权限
     * @param principals 权限列表
     * @return 认证信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // TODO 查询用户权限列表
        // TODO 把权限列表添加到info对象中
        return info;
    }

    /**
     * 认证，登录时调用
     * @param token 令牌
     * @return 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // TODO 从令牌中获取用户userId，校验id是否有效
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        // TODO 往info对象中添加用户信息，token字符串
        return info;
    }
}
