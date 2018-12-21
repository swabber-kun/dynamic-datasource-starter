package com.dynamic.common;


import lombok.Data;

/**
 * Response bean for format response
 *
 * @author jibingkun
 */
@Data
public class CommonResponse {

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public CommonResponse setCode(ResponseCode responseCode) {
        this.code = responseCode.code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "code: " + code + "message: " + message + "messageBody: " + data;
    }
}
