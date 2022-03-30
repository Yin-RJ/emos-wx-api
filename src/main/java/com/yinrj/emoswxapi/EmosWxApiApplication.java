package com.yinrj.emoswxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ServletComponentScan
@EnableFeignClients
public class EmosWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }

}
