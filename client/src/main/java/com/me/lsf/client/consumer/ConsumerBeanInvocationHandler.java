package com.me.lsf.client.consumer;

import com.alibaba.fastjson.JSON;
import com.me.lsf.client.common.ErrorCodeEnum;
import com.me.lsf.client.common.RpcResponseParam;
import com.me.lsf.common.http.client.LsfClient;
import com.me.lsf.common.model.LsfConnection;
import com.me.lsf.client.common.RpcParam;
import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.http.client.LsfHttpClientFactory;
import com.me.lsf.common.http.serialize.LsfSerialize;
import com.me.lsf.common.http.serialize.LsfSerializeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ConsumerBeanInvocationHandler implements InvocationHandler {

    private static Logger logger = LoggerFactory.getLogger(ConsumerBeanInvocationHandler.class);

    private Consumerbean consumerbean;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class tClass = consumerbean.getInterfaceClass();

        String canonicalName = tClass.getCanonicalName();
        String methodName = method.getName();

        ParentObject parentObject = consumerbean.getParentObject();

        boolean isNoRpc = isNoRpc(methodName, parentObject);

        if (isNoRpc) {
            return method.invoke(parentObject, args);
        }
        logger.debug("执行了 rpc 调用, class {}, method {}, args {}",canonicalName, methodName, Arrays.toString(args));

        String serializeType = consumerbean.getSerializeType();
        RpcParam rpcParam = getRpcParam(args, canonicalName, methodName, serializeType, method);

        LsfConnection connection = consumerbean.getConnection();

        String rpcResponseParamStr = getBody(rpcParam, connection);

        RpcResponseParam rpcResponseParam = JSON.parseObject(rpcResponseParamStr, RpcResponseParam.class);

        if (ErrorCodeEnum.SUCCESS.getCode().equals(rpcResponseParam.getCode())) {
            LsfSerialize lsfSerialize = LsfSerializeFactory.get(serializeType);
            Object result = lsfSerialize.deSerializeResult(method, rpcResponseParam.getResult());
            return result;
        } else {
            throw new RuntimeException(rpcResponseParam.getException());
        }

    }

    private String getBody(RpcParam rpcParam, LsfConnection connection) {
        LsfClient client = LsfHttpClientFactory.getClient();
        String host = connection.getHost();
        int port = connection.getPort();
        ClientParam clientParam = new ClientParam();
        clientParam.setHost(host);
        clientParam.setPort(port);
        clientParam.setUrl("/");

        String rpcBody = JSON.toJSONString(rpcParam);
        clientParam.setBody(rpcBody);
        return client.post(clientParam);
    }


    private RpcParam getRpcParam(Object[] args, String canonicalName, String methodName, String serializeType, Method method) {
        RpcParam rpcParam = new RpcParam();

        rpcParam.setrClass(canonicalName);
        rpcParam.setMethod(methodName);
        rpcParam.setSerializeType(serializeType);

        LsfSerialize lsfSerialize = LsfSerializeFactory.get(serializeType);

        String[] argsStrs = lsfSerialize.serializeParam(method, args);

        rpcParam.setArgs(argsStrs);
        return rpcParam;
    }

    private boolean isNoRpc(String methodName, ParentObject parentObject) {
        boolean isNoRpc = false;
        Method[] declaredMethods = parentObject.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                isNoRpc = true;
                break;
            }
        }
        return isNoRpc;
    }

    public void setConsumerbean(Consumerbean consumerbean) {
        this.consumerbean = consumerbean;
    }
}
