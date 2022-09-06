package com.me.lsf.common.http.server.netty.simple;

import com.me.lsf.common.http.server.AppServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(SimpleServerHandler.class);

    private AppServerHandler appServerHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        SimpleMessage msg = (SimpleMessage)obj;
        appServerHandler.asyncDeal(msg.getContent(),(result)->{
            SimpleMessage message = new SimpleMessage();
            message.setContent(result);

            ctx.writeAndFlush(message);
        });
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


    public void setAppServerHandler(AppServerHandler appServerHandler) {
        this.appServerHandler = appServerHandler;
    }
}
