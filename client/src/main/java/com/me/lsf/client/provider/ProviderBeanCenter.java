package com.me.lsf.client.provider;

import com.alibaba.fastjson.JSON;
import com.me.lsf.client.common.CenterUtil;
import com.me.lsf.client.common.ErrorCodeEnum;
import com.me.lsf.client.common.RpcParam;
import com.me.lsf.client.common.RpcResponseParam;
import com.me.lsf.common.Constants.Constants;
import com.me.lsf.common.http.serialize.LsfSerialize;
import com.me.lsf.common.http.serialize.LsfSerializeFactory;
import com.me.lsf.common.http.server.LsfHttpServer;
import com.me.lsf.common.http.server.LsfHttpServerFactory;
import com.me.lsf.common.model.*;
import com.me.lsf.common.utils.ExecutorServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProviderBeanCenter {

    private static Logger logger = LoggerFactory.getLogger(ProviderBeanCenter.class);

    private static Map<String, Object> beanNameMap = new HashMap<>();

    private static boolean isStart = false;

    private static Integer port = Constants.PROVIDER_START_PORT;

    public static void registerProvider(ProviderBean providerBean) {

        String className = providerBean.getInterfaceName();
        Object bean = providerBean.getImplObject();
        localRegister(className, bean);

        if (providerBean.getRegister()) {
            centerRegister(providerBean);
        }

    }

    private static void centerRegister(ProviderBean providerBean) {
        RegisterInfoBean registerInfoBean = new RegisterInfoBean();
        registerInfoBean.setKey(providerBean.getRegisterKey());
        LsfConnection lsfConnection = new LsfConnection();
        lsfConnection.setPort(port);
        lsfConnection.setHost(Constants.LOCAL_HOST);
        registerInfoBean.setLsfConnection(lsfConnection);
        registerInfoBean.setNeedResult(false);
        registerInfoBean.setType(RegisterTypeEnum.REGISTER_PROVIDER.getCode());
        try {
            String result = CenterUtil.centerRegister(providerBean.getRegistryBean(), registerInfoBean);
            logger.info("centerRegister result {}", result);
        } catch (Exception e) {
            logger.error("centerRegister timeout key {}", providerBean.getRegisterKey(), e);
        }
    }

    private static void localRegister(String className, Object bean) {
        beanNameMap.put(className, bean);

        if (!isStart) {
            isStart = true;

            LsfHttpServer server = LsfHttpServerFactory.getServer();
            server.setAppServerHandler(new ProviderAppServerHandler());

            ExecutorServiceFactory.getNewCachedThreadPool().execute(()->{
                port = Constants.PROVIDER_START_PORT;

                while (true) {
                    server.setPort(port);
                    try {
                        server.start();
                    } catch (BusinessException e) {
                        logger.error("port {} 被占用，自动 +1",port,e);
                        port++;
                    }
                }
            });

        }
    }

    public static Object getProvider(String className) {
        return beanNameMap.get(className);
    }


    public static String dealRequest(String body) {

        logger.info("center asyncDeal request {}",body);

        //解析 rpc 调用参数
        RpcParam rpcParam = JSON.parseObject(body, RpcParam.class);

        String rClassStr = rpcParam.getrClass();
        Object provider = getProvider(rClassStr);

        if (provider == null) {
            throw new RuntimeException("没有 对应的 provider");
        }

        String method = rpcParam.getMethod();
        String[] argsStr = rpcParam.getArgs();

        Class<?> aClass = provider.getClass();

        String resultStr = "error";
        RpcResponseParam rpcResponseParam = new RpcResponseParam();
        try {
            Method declaredMethod = null;

            //通过反射获取rpc调用的方法
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod1 : declaredMethods) {
                if (declaredMethod1.getName().equals(method)) {
                    declaredMethod = declaredMethod1;
                    break;
                }
            }

            if (declaredMethod == null) {
                throw new RuntimeException("没有这个方法 " + method);
            }

            //获取序列化实现类
            LsfSerialize lsfSerialize = LsfSerializeFactory.get(rpcParam.getSerializeType());

            //反序列参数
            Object[] inArgs = lsfSerialize.deSerializeParam(declaredMethod, argsStr);

            //调用实现类
            Object result = declaredMethod.invoke(provider, inArgs);

            //序列化执行结果
            String result1 = lsfSerialize.serializeResult(declaredMethod, result);
            rpcResponseParam.setCode(ErrorCodeEnum.SUCCESS.getCode());
            rpcResponseParam.setResult(result1);

        } catch (Exception e) {
            //若原始方法发生异常，则封装异常信息并返回给消费者
            rpcResponseParam.setCode(ErrorCodeEnum.EXCEPTION.getCode());
            rpcResponseParam.setException(e.toString());
            logger.error("lsf rpc exception rpc param {}", JSON.toJSONString(rpcParam), e);
        }
        resultStr = JSON.toJSONString(rpcResponseParam);

        logger.info("center asyncDeal result {}", resultStr);
        return resultStr;
    }
}
