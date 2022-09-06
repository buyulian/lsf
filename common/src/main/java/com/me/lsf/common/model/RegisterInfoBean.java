package com.me.lsf.common.model;

import com.me.lsf.common.model.LsfConnection;

public class RegisterInfoBean {

    private String key;

    private Integer type;

    private LsfConnection lsfConnection;

    private Boolean needResult;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LsfConnection getLsfConnection() {
        return lsfConnection;
    }

    public void setLsfConnection(LsfConnection lsfConnection) {
        this.lsfConnection = lsfConnection;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getNeedResult() {
        return needResult;
    }

    public void setNeedResult(Boolean needResult) {
        this.needResult = needResult;
    }
}
