package com.me.lsf.client.common;

public enum ErrorCodeEnum {
    SUCCESS(0,"成功"),
    EXCEPTION(1,"异常"),
    OTHER(2,"其他"),
    ;

    private Integer code;

    private String name;

    ErrorCodeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
