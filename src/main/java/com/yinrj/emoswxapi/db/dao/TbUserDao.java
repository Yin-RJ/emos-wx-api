package com.yinrj.emoswxapi.db.dao;

import com.yinrj.emoswxapi.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface TbUserDao {
    /**
     * 是否存在超级管理员
     * @return true表示存在
     */
    boolean haveRootUser();

    /**
     * 插入用户
     * @param user 用户信息
     * @return 插入用户的主键id
     */
    int insert(TbUser user);

    /**
     * 通过openid查询对应用户的主键id
     * @param openId openid
     * @return 主键id
     */
    int searchIdByOpenId(String openId);

    /**
     * 根据userid查询用户的权限
     * @param userId userid
     * @return 该用户的权限
     */
    Set<String> searchUserPermissions(int userId);
}