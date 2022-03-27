package com.yinrj.emoswxapi.common.config.xss;

import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description 抵御xss攻击的wrapper类
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        // 从请求中获取到的原始数据
        String value = super.getParameter(name);
        return filterValue(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        // 原始数据
        String[] values = super.getParameterValues(name);
        return filterValue(values);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> valueMap = new LinkedHashMap<>();
        if (map != null) {
            for (Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                valueMap.put(key, filterValue(entry.getValue()));
            }
        }
        return valueMap;
    }

    @Override
    public String getHeader(String name) {
        return filterValue(super.getHeader(name));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream inputStream = super.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        StringBuffer body = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        reader.close();
        inputStream.close();

        Map<String, Object> map = JSONUtil.parseObj(body.toString());
        Map<String, Object> result = new LinkedHashMap<>();

        for (Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                String res = filterValue(value.toString());
                result.put(entry.getKey(), res);
            } else {
                result.put(entry.getKey(), value);
            }
        }
        String jsonString = JSONUtil.toJsonStr(result);
        ByteArrayInputStream bais = new ByteArrayInputStream(jsonString.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    private static String filterValue(String value) {
        String result = value;
        if (StringUtils.hasLength(value)) {
            result = HtmlUtil.filter(value);
        }
        return result;
    }

    private static String[] filterValue(String[] values) {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = filterValue(values[i]);
            }
        }
        return values;
    }
}
