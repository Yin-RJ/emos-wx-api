package com.yinrj.emoswxapi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yinrj.emoswxapi.db.dao.TbUserDao;
import com.yinrj.emoswxapi.db.pojo.TbUser;
import com.yinrj.emoswxapi.exception.EmosException;
import com.yinrj.emoswxapi.feign.entity.OpenIdRequest;
import com.yinrj.emoswxapi.feign.service.OpenIdService;
import com.yinrj.emoswxapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description
 */
@Slf4j
@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {
    private static final String GRANT_TYPE = "authorization_code";

    private static final int VALID_STATUS = 1;

    private static final String ROOT_ROLE = "[0]";

    @Value("${emos.wx.app.app-id}")
    private String appId;

    @Value("${emos.wx.app.app-secret}")
    private String appSecret;

    @Resource
    private TbUserDao userDao;

    @Resource
    private OpenIdService openIdService;

    private String getOpenId(String code) {
        OpenIdRequest request = new OpenIdRequest();
        request.setAppId(appId);
        request.setSecret(appSecret);
        request.setJsCode(code);
        request.setGrantType(GRANT_TYPE);

        String openId = openIdService.getUserOpenId(request);

        if (openId == null) {
            throw new EmosException("openId为空，注册失败");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        //如果邀请码是000000，代表是超级管理员
        if (registerCode.equals("000000")) {
            //查询超级管理员帐户是否已经绑定
            boolean bool = userDao.haveRootUser();
            if (!bool) {
                //把当前用户绑定到ROOT帐户
                String openId = getOpenId(code);
                TbUser user = new TbUser();
                user.setStatus(VALID_STATUS);
                user.setNickname(nickname);
                user.setPhoto(photo);
                user.setRoot(true);
                user.setOpenId(openId);
                user.setCreateTime(DateUtil.date());
                user.setRole(ROOT_ROLE);
                return userDao.insert(user);
            } else {
                //如果root已经绑定了，就抛出异常
                throw new EmosException("无法绑定超级管理员账号");
            }
        }
        //TODO 此处还有其他判断内容
        else{
            return 0;
        }
    }
}
