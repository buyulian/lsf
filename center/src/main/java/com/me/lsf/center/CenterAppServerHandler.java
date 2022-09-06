package com.me.lsf.center;

import com.me.lsf.common.http.server.AppServerHandler;
import com.me.lsf.common.http.server.netty.simple.SimpleMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;

import java.util.function.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

public class CenterAppServerHandler implements AppServerHandler {
    private static final Logger logger = getLogger(CenterAppServerHandler.class);

    @Override
    public void asyncDeal(String content, Consumer<String> callBack) {
        String result = "";
        try {
            result = Center.dealRequest(content);
        } catch (Exception e) {
            logger.error("appServerHandler Exception",e);
            result = "appServerHandler Exception";
        }

        callBack.accept(result);

    }

    @Override
    public String synDeal(String content) {
        return Center.dealRequest(content);
    }
}
