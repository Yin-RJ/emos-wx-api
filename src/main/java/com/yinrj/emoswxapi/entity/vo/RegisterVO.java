package com.yinrj.emoswxapi.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description 注册的vo
 */
@Data
@ApiModel
public class RegisterVO {
    @NotBlank(message = "注册码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "注册码必须是6位数字")
    private String registerCode;

    @NotBlank(message = "微信临时授权码不能为空")
    private String code;

    @NotBlank(message = "微信昵称不能为空")
    private String nickname;

    @NotBlank(message = "头像地址不能为空")
    private String photo;
}
