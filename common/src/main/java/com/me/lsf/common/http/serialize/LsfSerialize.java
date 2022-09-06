package com.me.lsf.common.http.serialize;

import java.lang.reflect.Method;

public interface LsfSerialize {

    String[] serializeParam(Method method, Object[] args);

    Object[] deSerializeParam(Method method, String[] contents);

    String serializeResult(Method method, Object result);

    Object deSerializeResult(Method method, String content);

}
