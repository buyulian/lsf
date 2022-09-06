package com.me.lsf.common.http.server;

import com.me.lsf.common.http.server.netty.simple.NettySimpleServer;
import com.me.lsf.common.http.server.sun.JavaServer;

public class LsfHttpServerFactory {
    public static LsfHttpServer getServer() {
        return new NettySimpleServer();
    }
}
