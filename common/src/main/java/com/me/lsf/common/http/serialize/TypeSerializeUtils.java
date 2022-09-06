package com.me.lsf.common.http.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.me.lsf.common.utils.ReflectionUtil;

import java.util.*;

/**
 * @author buyulian
 * @date 2020/4/26
 */
public class TypeSerializeUtils {

    private static Object deSerializeObj(String str){
        TypeBean typeBean = JSON.parseObject(str, TypeBean.class);
        String type = typeBean.getType();

        if (type.equals(List.class.getCanonicalName())) {
            List<Object> objectList = deSerializeArray(typeBean.getValue());
            return objectList;
        } else if (type.equals(Set.class.getCanonicalName())) {
            List<Object> objectList = deSerializeArray(typeBean.getValue());
            return new HashSet<>(objectList);
        } else if (type.equals(Map.class.getCanonicalName())) {
            Map map1 = JSON.parseObject(typeBean.getValue(), Map.class);
            Map map = new HashMap();
            Set<Map.Entry> entrySet = map1.entrySet();
            for (Map.Entry entry : entrySet) {
                Object key = deSerializeObj(entry.getKey().toString());
                Object value = deSerializeObj(entry.getValue().toString());
                map.put(key, value);
            }
            return map;
        } else {
            Class<?> aClass = null;
            try {
                aClass = Class.forName(type);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Object obj = JSON.parseObject(typeBean.getValue(), aClass);
            return obj;
        }

    }

    private static List<Object> deSerializeArray(String str){
        List<TypeBean> typeBeanList = JSON.parseArray(str, TypeBean.class);
        List<Object> objectList = new LinkedList<>();
        for (TypeBean typeBean : typeBeanList) {
            Object obj = deSerializeObj(typeBean.getValue());
            objectList.add(obj);
        }
        return objectList;
    }

    private static String serializeObj(Object obj){
        TypeBean typeBean = new TypeBean();
        String type = obj.getClass().getCanonicalName();
        typeBean.setType(type);

        if (type.equals(List.class.getCanonicalName())) {

        } else if (type.equals(Set.class.getCanonicalName())) {

        } else if (type.equals(Map.class.getCanonicalName())) {

        } else {
            typeBean.setValue(JSON.toJSONString(obj));
        }
        return null;
    }


}
