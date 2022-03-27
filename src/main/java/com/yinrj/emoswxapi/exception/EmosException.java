package com.yinrj.emoswxapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yinrongjie
 * @version 1.0
 * @date 2022/3/27
 * @description 统一异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmosException extends RuntimeException{
    private Integer code = 500;
    private String msg;

    public EmosException(String message) {
        super(message);
        this.msg = message;
    }

    public EmosException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public EmosException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

    public EmosException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }
}
