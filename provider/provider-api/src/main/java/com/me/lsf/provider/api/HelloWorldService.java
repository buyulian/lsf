package com.me.lsf.provider.api;

import java.util.List;
import java.util.Map;

public interface HelloWorldService {

    HelloWorld getById(Long id);

    HelloWorld getByCondition(HelloWorld helloWorld);

    List<HelloWorld> getAll();

    ResponseDto<HelloWorld, User> getOneResponse();

    ResponseDto<HelloWorld, User> getOneResponseSuper(String name,
                                                      ResponseDto<HelloWorld, User> responseDto,
                                                      List<HelloWorld> helloWorldList);

    void add(HelloWorld helloWorld);

    String getNameById(Long id);

    Long getIdByName(String name);

    Map<String, HelloWorld> getMapByName();

    List<HelloWorld> getBigHelloWorlds();
}
