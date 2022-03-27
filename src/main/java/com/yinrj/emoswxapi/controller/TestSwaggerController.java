package com.yinrj.emoswxapi.controller;

import com.yinrj.emoswxapi.common.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description 测试swagger的controller
 */
@RestController
@RequestMapping("/test")
@Api("测试Swagger接口")
public class TestSwaggerController {
    @GetMapping("/say_hello")
    @ApiOperation("测试方法")
    public Result sayHello() {
        return Result.ok().put("message", "HelloWorld");
    }
}
