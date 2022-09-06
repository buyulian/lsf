package com.me.lsf.common.http.server;

import java.util.function.Consumer;

public interface AppServerHandler {

    void asyncDeal(String content, Consumer<String> callBack);

    String synDeal(String content);
}
