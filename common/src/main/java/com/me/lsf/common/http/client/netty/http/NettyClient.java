package com.me.lsf.common.http.client.netty.http;

import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.http.client.LsfClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NettyClient implements LsfClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    //线程组
    private EventLoopGroup group = new NioEventLoopGroup();

    //启动类
    private Bootstrap bootstrap = new Bootstrap();

    private ClientParam clientParam;

    public void start() throws InterruptedException {

        try {
            bootstrap.group(group)
                    .remoteAddress(new InetSocketAddress(clientParam.getHost(), clientParam.getPort()))
                    //长连接
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel channel) throws Exception {

                            //包含编码器和解码器
                            channel.pipeline().addLast(new HttpClientCodec());

                            //聚合
                            channel.pipeline().addLast(new HttpObjectAggregator(1024 * 10 * 1024));

                            //解压
                            channel.pipeline().addLast(new HttpContentDecompressor());

                            ClientHandler clientHandler = new ClientHandler();
                            clientHandler.setClientParam(clientParam);
                            channel.pipeline().addLast(clientHandler);
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect().sync();

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("NettyClient Exception", e);
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public String getContent() {
        CountDownLatch countDownLatch = clientParam.getCountDownLatch();
        try {
            countDownLatch.await(10*1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("http request timeout", e);
        }

        return clientParam.getNettyCilentResponse().getContent();
    }

    @Override
    public String post(ClientParam clientParam) {

        this.clientParam = clientParam;

        try {
            this.start();
            return this.getContent();
        } catch (InterruptedException e) {
            logger.error("registerProvider Exception key {}", e);
            throw new RuntimeException("centerRegister timeout key ",e);
        }
    }
}
