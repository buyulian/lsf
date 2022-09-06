package com.me.lsf.common.http.server.sun;

import com.me.lsf.common.http.server.AppServerHandler;
import com.me.lsf.common.http.server.LsfHttpServer;
import com.me.lsf.common.model.BusinessException;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaServer implements LsfHttpServer {

    private static Logger logger = LoggerFactory.getLogger(JavaServer.class);

    private int port = 26000;

    private AppServerHandler appServerHandler;

    @Override
    public void start(){
        HttpServer server = null;

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            logger.error("JavaServer IOException port {} ", port, e);
            throw new BusinessException(e);
        }

        HttpServerHandler httpHandler = new HttpServerHandler();
        httpHandler.setAppServerHandler(appServerHandler);
        server.createContext("/", httpHandler);

        logger.error("绑定端口 "+port);
        server.start();
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
