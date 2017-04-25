package com.alpha.common.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alpha.common.utils.SystemAmountUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Created by chenwen on 16/10/22.
 */
public class BigDecimalValueSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o != null && o instanceof BigDecimal) {
            BigDecimal value = (BigDecimal) o;
            jsonSerializer.write(value.setScale(SystemAmountUtil.amountScale, SystemAmountUtil.amountMath).toString());
        }
    }
}
