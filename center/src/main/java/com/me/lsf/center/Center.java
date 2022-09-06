package com.me.lsf.center;

import com.alibaba.fastjson.JSON;
import com.me.lsf.common.model.LsfConnection;
import com.me.lsf.common.model.RegisterInfoBean;
import com.me.lsf.common.model.RegisterTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Center {

    private static Logger logger = LoggerFactory.getLogger(Center.class);


    private static Map<String, LsfConnection> providerMap = new ConcurrentHashMap<>();

    private static Map<String, LsfConnection> consumerMap = new ConcurrentHashMap<>();

    public static void registerProvider(String key, LsfConnection lsfConnection) {
        providerMap.put(key, lsfConnection);
    }

    public static void registerConsumer(String key, LsfConnection lsfConnection) {
        consumerMap.put(key, lsfConnection);
    }

    public static LsfConnection getProviderLsfConnection(String key) {
        return providerMap.get(key);
    }


    public static String dealRequest(String body) {
        logger.info("center asyncDeal request {}",body);

        RegisterInfoBean registerInfoBean = JSON.parseObject(body, RegisterInfoBean.class);

        Integer type = registerInfoBean.getType();
        String key = registerInfoBean.getKey();

        String result = "error";
        if (RegisterTypeEnum.REGISTER_PROVIDER.getCode().equals(type)) {
            registerProvider(key, registerInfoBean.getLsfConnection());
            result = "register provider success";
        } else if (RegisterTypeEnum.GET_PROVIDER.getCode().equals(type)) {
            LsfConnection lsfConnection = getProviderLsfConnection(key);
            result = JSON.toJSONString(lsfConnection);
        }
        logger.info("center asyncDeal result {}", result);
        return result;
    }

}
