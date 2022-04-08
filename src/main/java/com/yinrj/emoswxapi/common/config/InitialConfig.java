package com.yinrj.emoswxapi.common.config;

import cn.hutool.core.util.StrUtil;
import com.yinrj.emoswxapi.db.dao.SysConfigDao;
import com.yinrj.emoswxapi.db.pojo.SysConfig;
import com.yinrj.emoswxapi.entity.constant.CheckInConstant;
import com.yinrj.emoswxapi.exception.EmosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/4/7
 * @description
 */
@Configuration
@Slf4j
public class InitialConfig {
    @Resource
    private SysConfigDao sysConfigDao;

    @Resource
    private CheckInConstant constant;

    @PostConstruct
    public void init(){
        log.info("开始执行初始化方法。。。。");
        List<SysConfig> configList = sysConfigDao.selectAllParam();
        configList.forEach(content -> {
            String key = content.getParamKey();
            key = StrUtil.toCamelCase(key);
            String value = content.getParamValue();
            Field field = null;
            try {
                field = constant.getClass().getDeclaredField(key);
                field.set(constant, value);
            } catch (Exception e) {
                log.error("初始化发生异常", e);
                throw new EmosException("初始化失败", e);
            }
        });
        log.info("初始化成功，constant={}", constant);
    }
}
