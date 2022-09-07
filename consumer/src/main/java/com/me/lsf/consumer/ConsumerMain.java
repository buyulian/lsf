package com.me.lsf.consumer;

import com.alibaba.fastjson.JSON;
import com.me.lsf.provider.api.HelloWorld;
import com.me.lsf.provider.api.HelloWorldService;
import com.me.lsf.provider.api.ResponseDto;
import com.me.lsf.provider.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConsumerMain {

    private static Logger logger = LoggerFactory.getLogger(ConsumerMain.class);

    public static void main(String[] args) {
        logger.info("app boot start");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-main.xml");
        logger.info("app boot successfully");

        HelloWorldService helloWorldService = (HelloWorldService) context.getBean("helloWorldService");

        getObjByMultiObj(helloWorldService);

        logger.info("hello world exist");

    }

    private static void getBigList(HelloWorldService helloWorldService) {
        List<HelloWorld> all = helloWorldService.getBigHelloWorlds();
        logger.info("hello world getBigHelloWorlds is {}", all.size());
    }

    private static void getObjByMultiObj(HelloWorldService helloWorldService) {
        HelloWorld helloWorld = new HelloWorld();
        long id = 1236L;
        helloWorld.setId(id);

        HelloWorld helloWorld3 = new HelloWorld();
        helloWorld3.setId(253L);
        helloWorld3.setName("abc");

        User user = new User();
        user.setId(2323L);
        user.setName("23abc");

        ResponseDto<HelloWorld, User> dto = new ResponseDto<>();
        dto.setCode(0);
        dto.setData(helloWorld3);
        dto.setrData(user);
        dto.setObj(helloWorld);
        List<HelloWorld> helloWorldList = getHelloWorlds();
        ResponseDto<HelloWorld, User> response2 = helloWorldService.getOneResponseSuper("tert0",
                dto,
                helloWorldList);
        logger.info("hello world name is "+ JSON.toJSONString(response2));
    }

    private static void getAllList(HelloWorldService helloWorldService, HelloWorld helloWorld) {
        List<HelloWorld> all = helloWorldService.getAll();
        for (HelloWorld world : all) {
            logger.info("hello world name is "+ JSON.toJSONString(world));
        }
        logger.info("hello world name is "+ JSON.toJSONString(all));

        logger.info("hello world name is "+ JSON.toJSONString(helloWorldService.getNameById(365L)));

        helloWorldService.add(helloWorld);
        logger.info("hello world name is add");

        logger.info("hello world name is "+ JSON.toJSONString(helloWorldService.getIdByName("null")));
    }

    private static void getById(HelloWorldService helloWorldService, HelloWorld helloWorld, long id) {
        HelloWorld helloWorldServiceById = helloWorldService.getById(id);
        String name = helloWorldServiceById.getName();
        logger.info("hello world name is "+name);


        HelloWorld helloWorldServiceByCondition = helloWorldService.getByCondition(helloWorld);
        logger.info("hello world name is "+helloWorldServiceByCondition.getName());

        Map<String, HelloWorld> helloWorldMap = helloWorldService.getMapByName();
        logger.info("hello world name is "+ JSON.toJSONString(helloWorldMap));


        ResponseDto<HelloWorld, User> response = helloWorldService.getOneResponse();
        logger.info("hello world name is "+ JSON.toJSONString(response));
    }


    private static List<HelloWorld> getHelloWorlds() {
        List<HelloWorld> helloWorldList = new LinkedList<>();
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setId(253L);
        helloWorld.setName("abc");
        helloWorldList.add(helloWorld);

        HelloWorld helloWorld1 = new HelloWorld();
        helloWorld1.setId(148L);
        helloWorld1.setName("asdfs");
        helloWorldList.add(helloWorld1);
        return helloWorldList;
    }

}
