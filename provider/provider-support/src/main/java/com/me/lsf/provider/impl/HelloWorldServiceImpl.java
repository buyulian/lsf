package com.me.lsf.provider.impl;

import com.me.lsf.provider.api.HelloWorld;
import com.me.lsf.provider.api.HelloWorldService;
import com.me.lsf.provider.api.ResponseDto;
import com.me.lsf.provider.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService {

    private static Logger logger = LoggerFactory.getLogger(HelloWorldServiceImpl.class);


    @Override
    public HelloWorld getById(Long id) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setId(id);
        helloWorld.setName("hello world getById");
        return helloWorld;
    }

    @Override
    public HelloWorld getByCondition(HelloWorld helloWorld) {
        helloWorld.setName("hello world getByCondition");
        return helloWorld;
    }

    @Override
    public List<HelloWorld> getAll() {
        List<HelloWorld> helloWorldList = getHelloWorlds();

        return helloWorldList;
    }

    @Override
    public ResponseDto<HelloWorld, User> getOneResponse() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setId(253L);
        helloWorld.setName("abc");

        User user = new User();
        user.setId(2323L);
        user.setName("23abc");

        ResponseDto<HelloWorld, User> dto = new ResponseDto<>();
        dto.setCode(0);
        dto.setData(helloWorld);
        dto.setrData(user);
        dto.setObj(helloWorld);

        return dto;
    }

    @Override
    public ResponseDto<HelloWorld, User> getOneResponseSuper(String name,
                                                             ResponseDto<HelloWorld, User> responseDto,
                                                             List<HelloWorld> helloWorldList) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setId(253L);
        helloWorld.setName("abc");

        User user = new User();
        user.setId(2323L);
        user.setName("23abc");

        ResponseDto<HelloWorld, User> dto = new ResponseDto<>();
        dto.setCode(0);
        dto.setData(helloWorld);
        dto.setrData(user);
        dto.setObj(helloWorld);

        return dto;
    }

    @Override
    public void add(HelloWorld helloWorld) {

    }

    @Override
    public String getNameById(Long id) {
        return "null";
    }

    @Override
    public Long getIdByName(String name) {

        if (name == null) {
            logger.error("getIdByName in param is null {}", name);
            return -1L;
        }

        logger.error("getIdByName in param {}", name);
        return 666L;
    }

    @Override
    public Map<String, HelloWorld> getMapByName() {
        Map<String, HelloWorld> map = new HashMap<>();
        List<HelloWorld> helloWorldList = getHelloWorlds();

        for (HelloWorld world : helloWorldList) {
            map.put(world.getName(), world);
        }
        return map;

    }

    private List<HelloWorld> getHelloWorlds() {
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

    @Override
    public List<HelloWorld> getBigHelloWorlds() {
        List<HelloWorld> helloWorldList = new LinkedList<>();

        for (int i = 0; i < 9999; i++) {
            HelloWorld helloWorld = new HelloWorld();
            helloWorld.setId((long) i);
            helloWorld.setName("abc"+i);
            helloWorldList.add(helloWorld);
        }
        return helloWorldList;
    }
}
