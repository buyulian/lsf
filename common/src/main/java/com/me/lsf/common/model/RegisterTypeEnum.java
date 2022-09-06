package com.me.lsf.common.model;

public enum RegisterTypeEnum {
    REGISTER_PROVIDER(1,"注册 provider"),
    REGISTER_CONSUMER(2,"注册 consumer"),
    GET_PROVIDER(3,"get provider"),
    ;

    private Integer code;

    private String name;

    RegisterTypeEnum(Integer code, String name) {
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
