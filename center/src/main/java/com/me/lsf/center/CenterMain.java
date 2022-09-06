package com.me.lsf.center;

import com.me.lsf.common.Constants.Constants;
import com.me.lsf.common.http.server.LsfHttpServer;
import com.me.lsf.common.http.server.LsfHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CenterMain {

    private static Logger logger = LoggerFactory.getLogger(CenterMain.class);


    public static void main(String[] args) {
        logger.info("CenterMain boot start");
        LsfHttpServer server = LsfHttpServerFactory.getServer();
        server.setAppServerHandler(new CenterAppServerHandler());
        int port = Constants.CENTER_PORT;
        server.setPort(port);
        try {
            server.start();
            logger.info("CenterMain boot successfully port {}", port);
        } catch (Exception e) {
            logger.error("CenterMain boot Exception", e);
        }
    }

}
