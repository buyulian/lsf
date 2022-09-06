package com.me.lsf.common.http.server.netty.simple;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class SimpleMessage {
    private Integer size;
    private String content;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
