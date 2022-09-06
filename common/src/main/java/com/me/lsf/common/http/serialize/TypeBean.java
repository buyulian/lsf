package com.me.lsf.common.http.serialize;

import java.util.List;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class TypeBean {

    private String type;

    private String name;

    private String value;

    private String typeBeanList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeBeanList() {
        return typeBeanList;
    }

    public void setTypeBeanList(String typeBeanList) {
        this.typeBeanList = typeBeanList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
