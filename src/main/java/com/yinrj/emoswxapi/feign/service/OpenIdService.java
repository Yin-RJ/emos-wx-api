package com.yinrj.emoswxapi.feign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinrj.emoswxapi.exception.EmosException;
import com.yinrj.emoswxapi.feign.OpenIdFeignService;
import com.yinrj.emoswxapi.feign.entity.OpenIdRequest;
import com.yinrj.emoswxapi.feign.entity.OpenIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description 获取用户的openid
 */
@Service
@Slf4j
public class OpenIdService {
    @Resource
    private OpenIdFeignService openIdFeignService;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    public String getUserOpenId(OpenIdRequest request) {
        String response = null;

        try {
            response = openIdFeignService.getOpenId(request.getAppId(), request.getSecret(), request.getJsCode(),
                    request.getGrantType());
        } catch (Exception e) {
            log.error("获取{}的openid发生异常", request.getJsCode(), e);
            throw new EmosException("获取openid发生异常", e);
        }

        if (response == null) {
            log.error("获取{}的openid为空", request.getJsCode());
            throw new EmosException("获取openid为空");
        }

        String openId = null;
        try {
            OpenIdResponse openIdContent = objectMapper.readValue(response, OpenIdResponse.class);
            openId = openIdContent.getOpenId();
        } catch (JsonProcessingException e) {
            log.error("解析openid异常, code={}", request.getJsCode(), e);
            throw new EmosException("解析openid异常", e);
        }


        return openId;
    }
}
