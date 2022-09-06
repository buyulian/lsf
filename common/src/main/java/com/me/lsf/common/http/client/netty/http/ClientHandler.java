package com.me.lsf.common.http.client.netty.http;

import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private ClientParam clientParam;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        FullHttpResponse response = (FullHttpResponse) msg;

        ByteBuf content = response.content();

        clientParam.getNettyCilentResponse().setContent(ByteBufUtils.convertByteBufToString(content));
        clientParam.getCountDownLatch().countDown();

        logger.debug("content: {}", content.toString(CharsetUtil.UTF_8));
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI(clientParam.getUrl());
        String msg = clientParam.getBody();

        //配置HttpRequest的请求数据和一些配置信息
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1,
                clientParam.getHttpMethod(),
                uri.toASCIIString(),
                Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        request.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                //开启长连接
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
                //设置传递请求内容的长度
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        //发送数据
        ctx.writeAndFlush(request);
    }

    public void setClientParam(ClientParam clientParam) {
        this.clientParam = clientParam;
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
        this.channelUnregistered(ctx);
        ctx.close();
    }
}
