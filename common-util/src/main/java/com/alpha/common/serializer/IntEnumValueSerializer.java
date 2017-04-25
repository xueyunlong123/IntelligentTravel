package com.alpha.common.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.xyl.common.enums.BaseIntEnum;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by chenwen on 16/10/22.
 */
public class IntEnumValueSerializer implements ObjectSerializer{
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o != null && o instanceof BaseIntEnum) {
            BaseIntEnum baseIntEnum = (BaseIntEnum) o;
            jsonSerializer.write(baseIntEnum.getName());
        }
    }
}



