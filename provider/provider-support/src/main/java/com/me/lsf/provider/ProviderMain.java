package com.me.lsf.provider;

import com.me.lsf.common.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderMain {

    private static Logger logger = LoggerFactory.getLogger(ProviderMain.class);

    public static void main(String[] args) {
        logger.info("app boot start");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-main.xml");
        logger.info("app boot successfully");

        CommonUtils.holdThread();
    }

}
