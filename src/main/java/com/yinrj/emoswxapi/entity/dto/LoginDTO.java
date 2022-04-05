package com.yinrj.emoswxapi.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/4/5
 * @description
 */
@ApiModel
@Data
public class LoginDTO {
    @NotBlank(message = "临时授权字符串不能为空")
    private String code;
}
