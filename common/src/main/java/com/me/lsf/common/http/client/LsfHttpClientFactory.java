package com.me.lsf.common.http.client;

import com.me.lsf.common.http.client.netty.simple.NettySimpleClient;

public class LsfHttpClientFactory {
    public static LsfClient getClient() {
        return NettySimpleClient.getSingleInstance();
    }
}
