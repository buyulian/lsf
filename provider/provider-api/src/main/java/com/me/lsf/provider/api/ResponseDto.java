package com.me.lsf.provider.api;

public class ResponseDto<T,R> {

    private Integer code;

    private T data;

    private Object obj;

    private R rData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public R getrData() {
        return rData;
    }

    public void setrData(R rData) {
        this.rData = rData;
    }
}
