package com.yinrj.emoswxapi.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description
 */
@Data
@ApiModel
public class TestSayHelloDTO {
    /**
     * 限制输入要求是一个2到15个汉字的姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,15}$")
    private String name;
}
