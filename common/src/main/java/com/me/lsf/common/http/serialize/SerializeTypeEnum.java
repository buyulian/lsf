package com.me.lsf.common.http.serialize;

public enum SerializeTypeEnum {
    JSON("JSON","JSON"),
    JSON_AUTO_TYPE("JSON_AUTO_TYPE","JSON_AUTO_TYPE"),
    ;

    private String code;

    private String name;

    SerializeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
