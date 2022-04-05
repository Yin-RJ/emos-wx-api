package com.yinrj.emoswxapi.service;

import java.util.Set;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description 用户服务
 */
public interface UserService {
    /**
     * 注册用户
     * @param registerCode 注册码
     * @param code 小程序传的临时授权字符串
     * @param nickname 微信昵称
     * @param photo 微信头像
     * @return 插入数据的主键
     */
    int registerUser(String registerCode, String code, String nickname, String photo);

    /**
     * 获取用户的权限
     * @param userId 用户id
     * @return 用户的权限
     */
    Set<String> getUserPermissions(int userId);

    /**
     * 登录
     * @param code 临时授权字符串
     * @return 用户的id
     */
    Integer login(String code);
}
