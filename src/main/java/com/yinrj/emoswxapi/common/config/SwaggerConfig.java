package com.yinrj.emoswxapi.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description Swagger的配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     *
     * @return Docket中存放的是swagger中需要用到的信息
     */
    @Bean
    public Docket createDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        // 设置swagger页面的基本信息
        ApiInfoBuilder builder = new ApiInfoBuilder();
        // 页面标题
        builder.title("EMOS在线办公系统");
        ApiInfo info = builder.build();
        docket.apiInfo(info);

        // 什么类的什么方法添加到swagger页面
        ApiSelectorBuilder selectorBuilder = docket.select();
        // 所有包的所有类
        selectorBuilder.paths(PathSelectors.any());
        // 限制只有加了apioperation注解的方法才会显示在swagger页面上
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        docket = selectorBuilder.build();

        // swagger支持jwt，规定在请求头中接受客户端的token信息作为校验信息
        ApiKey apiKey = new ApiKey("token", "token", "header");
        List<ApiKey> apiKeyList = Collections.singletonList(apiKey);
        docket.securitySchemes(apiKeyList);

        // 令牌的作用域 配置成全局
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = {authorizationScope};
        SecurityReference reference = new SecurityReference("token", scopes);
        List<SecurityReference> referenceList = Collections.singletonList(reference);
        SecurityContext context = SecurityContext.builder().securityReferences(referenceList).build();
        List<SecurityContext> contextList = Collections.singletonList(context);
        docket.securityContexts(contextList);

        return docket;
    }
}
