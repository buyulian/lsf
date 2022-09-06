package com.me.lsf.client.consumer;

import java.lang.reflect.Proxy;

public class ConsumerBeanFactory {

    public static <T> T create(Consumerbean consumerbean) {
        ConsumerBeanInvocationHandler invocationHandler = new ConsumerBeanInvocationHandler();
        invocationHandler.setConsumerbean(consumerbean);
        Class rpcClass = consumerbean.getInterfaceClass();

        T t = (T) Proxy.newProxyInstance(rpcClass.getClassLoader()
                , new Class<?>[]{rpcClass}, invocationHandler);
        return t;
    }

}
