package com.me.lsf.common.http.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.me.lsf.common.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class JsonAutoTypeSerialize implements LsfSerialize {

    private static Logger logger = LoggerFactory.getLogger(JsonAutoTypeSerialize.class);

    {
        ParserConfig.getGlobalInstance().setSafeMode(false);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public String[] serializeParam(Method method, Object[] args) {

        String[] argsStrs = null;
        if (args != null) {
            argsStrs = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                argsStrs[i] = JSON.toJSONString(args[i], SerializerFeature.WriteClassName);
            }
        }
        return argsStrs;
    }

    @Override
    public Object[] deSerializeParam(Method method, String[] contents) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        return getInArgs(contents, parameterTypes, method);
    }

    private Object[] getInArgs(String[] strs, Class<?>[] parameterTypes, Method method) {

        if (strs == null) {
            return null;
        }
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Object[] inArgs = new Object[strs.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            inArgs[i] = getObjectSuper(strs[i], parameterType, genericParameterTypes[i]);
        }
        return inArgs;
    }

    @Override
    public String serializeResult(Method method, Object result) {
        return JSON.toJSONString(result, SerializerFeature.WriteClassName);
    }

    @Override
    public Object deSerializeResult(Method method, String content) {
        Type genericReturnType = method.getGenericReturnType();
        Class<?> returnType = method.getReturnType();
        return getObjectSuper(content, returnType, genericReturnType);
    }

    private Object getObjectSuper(String content, Class<?> returnType, Type genericReturnType) {
        Object result = JSON.parseObject(content, returnType);
        return result;
    }
}
