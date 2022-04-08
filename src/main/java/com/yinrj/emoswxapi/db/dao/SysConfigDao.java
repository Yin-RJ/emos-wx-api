package com.yinrj.emoswxapi.db.dao;

import com.yinrj.emoswxapi.db.pojo.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysConfigDao {
    /**
     * 获取配置表中的所有参数
     * @return 配置表中所有内容
     */
    List<SysConfig> selectAllParam();
}