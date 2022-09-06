package com.me.lsf.client.provider;

import com.me.lsf.common.http.server.AppServerHandler;
import com.me.lsf.common.http.server.netty.simple.SimpleMessage;
import com.me.lsf.common.utils.ExecutorServiceFactory;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;

import java.util.function.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

public class ProviderAppServerHandler implements AppServerHandler {
    private static final Logger logger = getLogger(ProviderAppServerHandler.class);

    @Override
    public void asyncDeal(String content, Consumer<String> callBack) {
        ExecutorServiceFactory.getNewCachedThreadPool().execute(()->{
            String result = "";
            try {
                result = ProviderBeanCenter.dealRequest(content);
            } catch (Exception e) {
                logger.error("appServerHandler Exception",e);
                result = "appServerHandler Exception";
            }

            callBack.accept(result);
        });
    }

    @Override
    public String synDeal(String content) {
        return ProviderBeanCenter.dealRequest(content);
    }
}
