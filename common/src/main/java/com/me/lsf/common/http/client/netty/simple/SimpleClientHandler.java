package com.me.lsf.common.http.client.netty.simple;

import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.http.server.netty.simple.SimpleMessage;
import com.me.lsf.common.http.server.netty.simple.SimpleServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class SimpleClientHandler  extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        SimpleMessage msg = (SimpleMessage)obj;
        String content = msg.getContent();

        ClientParam clientParam = DefaultFuture.getChannelClientParamMap().get(ctx.channel());

        clientParam.getNettyCilentResponse().setContent(content);
        clientParam.getCountDownLatch().countDown();

        logger.debug("content: {}", content);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) {
            cause.printStackTrace();
        }
        if(null != ctx) {
            ctx.close();
        }
    }

}