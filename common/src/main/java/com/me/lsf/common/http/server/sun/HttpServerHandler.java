package com.me.lsf.common.http.server.sun;

import com.me.lsf.common.http.server.AppServerHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class HttpServerHandler implements HttpHandler {

    private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);


    private AppServerHandler appServerHandler;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "hello world";

        try{

            String postString = IOUtils.toString(exchange.getRequestBody());

            logger.debug("HttpServerHandler request {}", postString);

            try {
                response = appServerHandler.synDeal(postString);
            } catch (Exception e) {
                response = "Exception "+e.getMessage();
            }

            logger.debug("HttpServerHandler response {}", response);

            exchange.sendResponseHeaders(200,0);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (IOException ie) {

        } catch (Exception e) {

        }
    }

    public void setAppServerHandler(AppServerHandler appServerHandler) {
        this.appServerHandler = appServerHandler;
    }
}
