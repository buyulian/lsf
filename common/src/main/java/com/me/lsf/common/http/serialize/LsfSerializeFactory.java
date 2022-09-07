package com.me.lsf.common.http.serialize;

public class LsfSerializeFactory {

    public static LsfSerialize get(String serializeType) {
        if (SerializeTypeEnum.JSON.getCode().equals(serializeType)) {
            return new JsonSerialize();
        } else if (SerializeTypeEnum.JSON_AUTO_TYPE.getCode().equals(serializeType)) {
            return new JsonAutoTypeSerialize();
        } else {
            throw new UnsupportedOperationException("未支持的序列化方式 " + serializeType);
        }
    }

}
