package com.yinrj.emoswxapi.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description 统一返回格式
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Result extends HashMap<String, Object> {
    public Result(int code, String statusMsg) {
        put("code", code);
        put("msg", statusMsg);
    }

    /**
     * 为了实现链式调用
     * @param key 传入的key
     * @param value 传入的value
     * @return 插入了数据的result
     */
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static Result ok() {
        return new Result(HttpStatus.SC_OK, "success");
    }

    public static Result ok(String msg) {
        Result result = ok();
        return result.put("msg", msg);
    }

    public static Result ok(Map<String, Object> map) {
        Result result = ok();
        result.putAll(map);
        return result;
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg);
    }

    public static Result error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error() {
        return error("未知异常，请联系管理员");
    }
}
