package com.me.lsf.common.http.server;

public interface LsfHttpServer {

    void start();

    void setPort(int port);

    void setAppServerHandler(AppServerHandler appServerHandler);
}
