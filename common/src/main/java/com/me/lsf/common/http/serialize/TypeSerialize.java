package com.me.lsf.common.http.serialize;

import com.alibaba.fastjson.JSON;
import com.me.lsf.common.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class TypeSerialize implements LsfSerialize {

    private static Logger logger = LoggerFactory.getLogger(TypeSerialize.class);


    @Override
    public String[] serializeParam(Method method, Object[] args) {

        String[] argsStrs = null;
        if (args != null) {
            argsStrs = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                argsStrs[i] = JSON.toJSONString(args[i]);
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
        return JSON.toJSONString(result);
    }

    @Override
    public Object deSerializeResult(Method method, String content) {
        Type genericReturnType = method.getGenericReturnType();
        Class<?> returnType = method.getReturnType();
        return getObjectSuper(content, returnType, genericReturnType);
    }

    private Object getObjectSuper(String content, Class<?> returnType, Type genericReturnType) {

        Object result = JSON.parseObject(content, returnType);

        if (genericReturnType == null) {
            return result;
        }

        if (genericReturnType instanceof ParameterizedType) {
            Type[] genericTypes = ((ParameterizedType) genericReturnType).getActualTypeArguments();

            if (result instanceof List) {
                List list = (List)result;
                Type genericType = genericTypes[0];
                for (int i = 0; i < list.size(); i++) {
                    Object item = list.get(i);
                    Object element = getObjectJsonTwo(genericType, item);
                    list.set(i, element);
                }
            } else if (result instanceof Set) {
                Set set = (Set) result;
                Type genericType = genericTypes[0];

                Set newSet = new HashSet();

                for (Object item : set) {
                    Object element = getObjectJsonTwo(genericType, item);
                    newSet.add(element);
                }
                result = newSet;
            } else if (result instanceof Map) {
                Map map = (Map) result;
                Type genericType = genericTypes[0];
                Type genericTypeB = genericTypes[1];

                Map newMap = new HashMap();

                Set<Map.Entry> entrySet = map.entrySet();
                for (Map.Entry item : entrySet) {
                    Object element = getObjectJsonTwo(genericType, item.getKey());
                    Object elementB = getObjectJsonTwo(genericTypeB, item.getValue());
                    newMap.put(element, elementB);
                }

                result = newMap;
            } else {

                Map<String, Field> fieldMap = new HashMap<>();
                Integer cur = 0;
                for (Field field : returnType.getDeclaredFields()) {
                    String methodName = "getGenericSignature";

                    try {
                        Object signObj = ReflectionUtil.callMethod(field, methodName, null, null);

                        if (signObj != null) {
                            fieldMap.put(cur.toString(), field);
                            cur++;
                        }


                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < genericTypes.length; i++) {
                    Type genericType = genericTypes[i];
                    logger.debug("泛型类型："+genericType);

                    Field field = fieldMap.get(String.valueOf(i));

                    Object obj = null;
                    try {
                        field.setAccessible(true);
                        obj = field.get(result);
                        Object two = getObjectJsonTwo(genericType, obj);
                        field.set(result, two);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

        return result;
    }

    private Object getObjectJsonTwo(Type genericType, Object item) {
        String text = JSON.toJSONString(item);
        return JSON.parseObject(text, genericType);
    }
}
