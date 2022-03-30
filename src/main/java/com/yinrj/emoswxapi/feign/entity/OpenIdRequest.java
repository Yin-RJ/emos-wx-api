package com.yinrj.emoswxapi.feign.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/29
 * @description 获取openid的请求参数
 */
@Data
public class OpenIdRequest {
    @JsonProperty("appid")
    private String appId;

    private String secret;

    @JsonProperty("js_code")
    private String jsCode;

    @JsonProperty("grant_type")
    private String grantType;
}
