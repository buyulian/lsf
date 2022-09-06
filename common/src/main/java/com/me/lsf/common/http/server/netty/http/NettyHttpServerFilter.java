package com.me.lsf.common.http.server.netty.http;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyHttpServerFilter extends ChannelInitializer<SocketChannel> {

    private ChannelInboundHandlerAdapter channelInboundHandlerAdapter;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        //处理http服务的关键handler
//        ph.addLast("encoder",new HttpResponseEncoder());
//        ph.addLast("decoder",new HttpRequestDecoder());
        ph.addLast(new HttpServerCodec());
        ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
        ph.addLast("handler", channelInboundHandlerAdapter);// 服务端业务逻辑
    }

    public void setChannelInboundHandlerAdapter(ChannelInboundHandlerAdapter channelInboundHandlerAdapter) {
        this.channelInboundHandlerAdapter = channelInboundHandlerAdapter;
    }
}
