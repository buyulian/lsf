package com.me.lsf.common.http.client.netty.simple;

import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buyulian
 * @date 2020/4/3
 * 谨慎使用，增加了一定要记得释放
 */
public class ClientShutDownHookThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ClientShutDownHookThread.class);

    private EventLoopGroup group;

    public ClientShutDownHookThread(EventLoopGroup group) {
        this.group = group;
    }

    @Override
    public void run() {
        logger.error("ClientShutDownHookThread clean begin");
        group.shutdownGracefully();
        logger.error("ClientShutDownHookThread clean end");
    }

}
