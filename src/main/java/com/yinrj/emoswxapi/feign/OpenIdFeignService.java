package com.yinrj.emoswxapi.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description
 */
@FeignClient(name = "get-open-id", url = "${emos.http-url.wx-openid}")
public interface OpenIdFeignService {
    /**
     * 获取用户的openid
     * @param appid 小程序的appid
     * @param secret 小程序的密钥
     * @param code 临时授权字符串
     * @param grantType 固定值
     * @return openid
     */
    @GetMapping
    String getOpenId(@RequestParam("appid") String appid, @RequestParam("secret") String secret, @RequestParam(
            "js_code") String code, @RequestParam("grant_type") String grantType);
}
