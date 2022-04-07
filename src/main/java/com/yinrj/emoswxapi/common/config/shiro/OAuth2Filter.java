package com.yinrj.emoswxapi.common.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yinrj.emoswxapi.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description shiro的过滤器，要使用threadlocal所以用多例
 */
@Component
@Scope("prototype")
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {
    private static final String TOKEN = "token";

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpireTime;

    @Resource
    private ThreadLocalToken threadLocalToken;

    @Resource
    private JwtUtil jwtUtil;

    @Resource(name = "common-redis-template")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 拦截请求后，用于把字符串封装成令牌对象
     * @param request 请求
     * @param response 响应
     * @return 封装的令牌对象
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        // 从请求中获取令牌
        String token = getTokenFromRequest((HttpServletRequest) request);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        // 返回封装的令牌对象
        return new Token2OAuth(token);
    }

    /**
     * 拦截请求，判断是否要被shiro框架处理
     * @param request 请求
     * @param response 响应
     * @param mappedValue
     * @return true不需要被处理
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        // option类的请求不处理
        return RequestMethod.OPTIONS.name().equals(req.getMethod());
    }

    /**
     * 处理所有需要被shiro处理的请求
     * @param request 请求
     * @param response 响应
     * @return true需要被处理
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        // 设置跨域
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));

        threadLocalToken.clearToken();

        String token = getTokenFromRequest(req);
        if (StrUtil.isBlank(token)) {
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的令牌");
            return false;
        }

        // 验证token是否有效，内容+时间
        try {
            jwtUtil.verifierToken(token);
        } catch (TokenExpiredException e) {
            // 过期异常
            if (Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
                updateToken(token);
            } else {
                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
                resp.getWriter().print("令牌已经过期");
                return false;
            }
        } catch (JWTDecodeException e) {
            // 内容异常
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的令牌");
            return false;
        }
        // 间接调用realm类进行认证授权
        return executeLogin(request, response);
    }

    /**
     * 认证失败后调用的方法
     * @param token 令牌
     * @param e 失败异常
     * @param request 请求
     * @param response 响应
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        // 设置跨域
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);

        try {
            resp.getWriter().print(e.getMessage());
        } catch (Exception exception) {
            log.error("认证失败", exception);
        }

        return false;
    }

    /**
     * 从请求中获取令牌
     * @param request 请求
     * @return 令牌
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        if (StrUtil.isBlank(token)) {
            // 请求头中如果没有就再从请求体中取一次
            token = request.getParameter(TOKEN);
        }
        return token;
    }

    /**
     * 更新token
     * @param oldToken 老的token
     */
    private void updateToken(String oldToken) {
        // 删除旧的token
        redisTemplate.delete(oldToken);
        int userId = jwtUtil.getUserId(oldToken);
        String newToken = jwtUtil.createToken(userId);
        redisTemplate.opsForValue().set(newToken, userId, cacheExpireTime, TimeUnit.DAYS);
        threadLocalToken.setToken(newToken);
    }
}
