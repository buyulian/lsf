package com.me.lsf.common.http.client.http.client;

import com.me.lsf.common.http.client.ClientParam;
import com.me.lsf.common.http.client.LsfClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Client implements LsfClient {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    private CloseableHttpClient httpclient = HttpClients.createDefault();

    private Map<String,String> defaultHeader =new HashMap<>();

    {
        defaultHeader.put("Content-type", "application/x-www-form-urlencoded");
        defaultHeader.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        defaultHeader.put("Accept-Encoding","gzip,deflate,sdch");
        defaultHeader.put("Accept-Language","zh-CN,zh;q=0.8");
        defaultHeader.put("Connection","keep-alive");
        defaultHeader.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");

    }

    private String encoding= HTTP.UTF_8;

    @Override
    public String post(ClientParam clientParam) {
        String uri = "http://"+clientParam.getHost() + ":" + clientParam.getPort() + clientParam.getUrl();
        HttpPost httpPost = new HttpPost(uri);
        //第三步：给httpPost设置JSON格式的参数
        String body1 = clientParam.getBody();
        logger.info("client request {}", body1);
        StringEntity requestEntity = new StringEntity(body1,"utf-8");
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);
        defaultHeader.forEach(httpPost::setHeader);

        String body= null;
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            body = null;
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("client body {}", body);
        return body;
    }
}
