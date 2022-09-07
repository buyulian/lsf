package com.me.lsf.client.common;

import com.me.lsf.common.http.serialize.SerializeTypeEnum;

public class RpcParam {

    private String rClass;

    private String method;

    private String[] args;

    private String serializeType = SerializeTypeEnum.JSON_AUTO_TYPE.getCode();

    public String getrClass() {
        return rClass;
    }

    public void setrClass(String rClass) {
        this.rClass = rClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(String serializeType) {
        this.serializeType = serializeType;
    }
}
