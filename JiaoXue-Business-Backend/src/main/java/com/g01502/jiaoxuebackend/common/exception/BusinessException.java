package com.g01502.jiaoxuebackend.common.exception;

/**
 * 业务异常，统一封装业务错误码和错误信息。
 */
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
