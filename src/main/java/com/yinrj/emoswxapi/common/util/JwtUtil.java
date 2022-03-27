package com.yinrj.emoswxapi.common.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yinrj.emoswxapi.exception.EmosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description jwt相关工具类
 */
@Component
@Slf4j
public class JwtUtil {
    /**
     * JWT令牌中userid的存储key
     */
    private static final String TOKEN_KEY = "userId";
    /**
     * jwt的密钥
     */
    @Value("${emos.jwt.secret}")
    private String secret;
    /**
     * 过期时间（天）
     */
    @Value("${emos.jwt.expire}")
    private int expire;

    /**
     * 生成jwt的令牌
     * @param userId 用户id
     * @return 令牌
     */
    public String createToken(int userId) {
        // 过期日期
        Date expireDate = DateUtil.offsetDay(DateUtil.date(), expire);
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim(TOKEN_KEY, userId).withExpiresAt(expireDate).sign(getAlgorithm());
    }


    /**
     * 通过jwt的令牌获得userid
     * @param token jwt令牌
     * @return userid
     */
    public int getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(TOKEN_KEY).asInt();
        } catch (Exception e) {
            throw new EmosException("令牌解析失败", e);
        }
    }

    /**
     * 校验令牌的有效性，验证失败直接抛异常
     * @param token jwt令牌
     */
    public void verifierToken(String token) {
        // 解密用的验证对象
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        verifier.verify(token);
    }


    /**
     * 加密算法
     * @return 获得加密算法
     */
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
}
