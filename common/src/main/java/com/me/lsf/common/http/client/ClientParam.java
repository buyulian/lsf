package com.me.lsf.common.http.client;

import io.netty.handler.codec.http.HttpMethod;

import java.util.concurrent.CountDownLatch;

public class ClientParam {

    private int port;

    private String host;

    private String url;

    private String body;

    private HttpMethod httpMethod = HttpMethod.POST;

    private NettyCilentResponse nettyCilentResponse = new NettyCilentResponse();

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public NettyCilentResponse getNettyCilentResponse() {
        return nettyCilentResponse;
    }

    public void setNettyCilentResponse(NettyCilentResponse nettyCilentResponse) {
        this.nettyCilentResponse = nettyCilentResponse;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
