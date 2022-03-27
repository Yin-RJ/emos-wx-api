package com.yinrj.emoswxapi.controller;

import com.yinrj.emoswxapi.common.response.Result;
import com.yinrj.emoswxapi.entity.dto.TestSayHelloDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/say_sb_hello")
    @ApiOperation("带有参数的测试方法")
    public Result sayHello2Person(@Validated @RequestBody TestSayHelloDTO helloDTO) {
        return Result.ok().put("message", "Hello, " + helloDTO.getName());
    }
}
