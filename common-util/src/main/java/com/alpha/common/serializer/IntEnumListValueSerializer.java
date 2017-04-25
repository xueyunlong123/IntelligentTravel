package com.alpha.common.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.xyl.common.enums.BaseIntEnum;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by chenwen on 16/10/22.
 */
public class IntEnumListValueSerializer implements ObjectSerializer{
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o != null && o instanceof Set) {
            Set<BaseIntEnum> baseIntEnumSet = (Set<BaseIntEnum>) o;
            Set<String> result = baseIntEnumSet.stream().map(BaseIntEnum::getName).collect(Collectors.toSet());
            jsonSerializer.write(result);
        }
    }
}
