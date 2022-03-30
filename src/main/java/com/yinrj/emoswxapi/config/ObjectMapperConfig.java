package com.yinrj.emoswxapi.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/30
 * @description
 */
@Configuration
public class ObjectMapperConfig {
    @Bean("objectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true) ;
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return mapper;
    }
}
