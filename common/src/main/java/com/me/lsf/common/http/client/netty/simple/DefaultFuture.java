package com.me.lsf.common.http.client.netty.simple;

import com.me.lsf.common.http.client.ClientParam;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultFuture {

    private static Map<String, ChannelFuture> channelFutureMap = new ConcurrentHashMap<>();

    private static Map<Channel, ClientParam> channelClientParamMap = new ConcurrentHashMap<>();


    public static Map<String, ChannelFuture> getChannelFutureMap() {
        return channelFutureMap;
    }

    public static Map<Channel, ClientParam> getChannelClientParamMap() {
        return channelClientParamMap;
    }
}
