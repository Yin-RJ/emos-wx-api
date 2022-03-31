package com.yinrj.emoswxapi.controller;

import com.yinrj.emoswxapi.common.response.Result;
import com.yinrj.emoswxapi.common.util.JwtUtil;
import com.yinrj.emoswxapi.entity.dto.RegisterDTO;
import com.yinrj.emoswxapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description
 */
@RestController
@RequestMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
@Api("用户服务")
public class UserController {
    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;


    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Result register(@Valid @RequestBody RegisterDTO registerDTO) {
        int userId = userService.registerUser(registerDTO.getRegisterCode(), registerDTO.getCode(),
                registerDTO.getNickname(), registerDTO.getPhoto());
        Set<String> userPermissions = userService.getUserPermissions(userId);
        String token = jwtUtil.createToken(userId);
        redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
        return Result.ok("用户注册成功").put("token", token).put("permissions", userPermissions);
    }
}
