package com.me.lsf.common.http.server.netty.simple;

import com.me.lsf.common.http.client.netty.simple.ClientShutDownHookThread;
import com.me.lsf.common.http.server.AppServerHandler;
import com.me.lsf.common.http.server.LsfHttpServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettySimpleServer implements LsfHttpServer {

    private static Logger logger = LoggerFactory.getLogger(NettySimpleServer.class);

    private int port = 26000; //设置服务端端口

    private EventLoopGroup group = new NioEventLoopGroup();

    private ServerBootstrap b = new ServerBootstrap();

    private AppServerHandler appServerHandler;

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    @Override
    public void start() {
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            ChannelInitializer childHandler = new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    ChannelPipeline ch = sc.pipeline();
                    ch.addLast(new SimpleDecoder());
                    ch.addLast(new SimpleEncoder());
                    SimpleServerHandler simpleHandler = new SimpleServerHandler();
                    simpleHandler.setAppServerHandler(appServerHandler);
                    ch.addLast(simpleHandler);
                }
            };
            b.childHandler(childHandler); //设置过滤器
            b.option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(new InetSocketAddress(port)).sync();
            logger.info("lsf 服务端启动成功,端口是: {}", port);

            Runtime.getRuntime().addShutdownHook(new ClientShutDownHookThread(group));

            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully(); //关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setAppServerHandler(AppServerHandler appServerHandler) {
        this.appServerHandler = appServerHandler;
    }

}
