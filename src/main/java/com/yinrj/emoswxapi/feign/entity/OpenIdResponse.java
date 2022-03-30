package com.yinrj.emoswxapi.feign.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenIdResponse {
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("errcode")
    private String errCode;
    @JsonProperty("errmsg")
    private String errMsg;
}
