package com.me.lsf.common.http.client.netty.simple;

import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.http.client.LsfClient;
import com.me.lsf.common.http.server.netty.simple.SimpleDecoder;
import com.me.lsf.common.http.server.netty.simple.SimpleEncoder;
import com.me.lsf.common.http.server.netty.simple.SimpleMessage;
import com.me.lsf.common.model.BusinessException;
import com.me.lsf.common.utils.ExecutorServiceFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NettySimpleClient implements LsfClient {

    private static Logger logger = LoggerFactory.getLogger(NettySimpleClient.class);

    //线程组
    private EventLoopGroup group = new NioEventLoopGroup();

    //启动类
    private Bootstrap bootstrap = new Bootstrap();

    private boolean started = false;

    public void start() throws InterruptedException {

        try {
            bootstrap.group(group)
                    //长连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline ch = sc.pipeline();
                            ch.addLast(new SimpleDecoder());
                            ch.addLast(new SimpleEncoder());
                            ch.addLast(new SimpleClientHandler());
                        }
                    });
            Runtime.getRuntime().addShutdownHook(new ClientShutDownHookThread(group));

        } catch (Exception e) {
            logger.error("NettyClient Exception", e);
            throw new RuntimeException(e);
        }
    }

    private String getKey(String host, int port) {
        return host + ":" + port;
    }

    @Override
    public String post(ClientParam clientParam) {

        try {

            if (!started) {
                started = true;
                start();
            }

            String key = getKey(clientParam.getHost(), clientParam.getPort());
            ChannelFuture channelFuture = DefaultFuture.getChannelFutureMap().get(key);

            if (channelFuture == null) {
                channelFuture = bootstrap.connect(clientParam.getHost(), clientParam.getPort()).sync();
                DefaultFuture.getChannelFutureMap().put(key, channelFuture);

            }

            String msg = clientParam.getBody();
            clientParam.setCountDownLatch(new CountDownLatch(1));
            SimpleMessage message = new SimpleMessage();
            message.setContent(msg);

            //发送数据
            Channel channel = channelFuture.channel();

            DefaultFuture.getChannelClientParamMap().put(channel, clientParam);

            channel.writeAndFlush(message);

            CountDownLatch countDownLatch = clientParam.getCountDownLatch();
            try {
                countDownLatch.await(100*1000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException("http request timeout", e);
            }

            long count = countDownLatch.getCount();
            if (count > 0) {
                throw new BusinessException("等待回应超时, key "+key+" ,msg "+msg);
            }

            return clientParam.getNettyCilentResponse().getContent();

        } catch (Exception e) {
            logger.error("registerProvider Exception key {}", e);
            throw new RuntimeException("centerRegister timeout key ",e);
        }
    }

    static class Single {
        public static NettySimpleClient client = new NettySimpleClient();
    }

    public static NettySimpleClient getSingleInstance() {
        return Single.client;
    }
}
