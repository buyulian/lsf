package com.me.lsf.client.common;

import com.alibaba.fastjson.JSON;
import com.me.lsf.client.registry.RegistryBean;
import com.me.lsf.common.Constants.Constants;
import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.http.client.LsfClient;
import com.me.lsf.common.http.client.LsfHttpClientFactory;
import com.me.lsf.common.model.LsfConnection;
import com.me.lsf.common.model.RegisterInfoBean;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class CenterUtil {

    private static final Logger logger = getLogger(CenterUtil.class);

    public static String centerRegister(RegistryBean registryBean, RegisterInfoBean registerInfoBean) {
        LsfClient client = LsfHttpClientFactory.getClient();
        ClientParam clientParam = new ClientParam();
        LsfConnection lsfConnection = registryBean.getLsfConnection();
        clientParam.setHost(lsfConnection.getHost());
        clientParam.setPort(lsfConnection.getPort());
        clientParam.setHttpMethod(HttpMethod.POST);
        clientParam.setUrl("/");

        String body = JSON.toJSONString(registerInfoBean);
        clientParam.setBody(body);

        String key = registerInfoBean.getKey();
        try {
            return client.post(clientParam);
        } catch (Exception e) {
            logger.error("registerProvider Exception key {}", key, e);
            throw new RuntimeException("centerRegister timeout key " + key,e);
        }
    }
}
