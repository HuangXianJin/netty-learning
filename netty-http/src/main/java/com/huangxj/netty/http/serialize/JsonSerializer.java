package com.huangxj.netty.http.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName JsonSerializer
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-11 15:33
 * @Version V1.0
 **/
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
