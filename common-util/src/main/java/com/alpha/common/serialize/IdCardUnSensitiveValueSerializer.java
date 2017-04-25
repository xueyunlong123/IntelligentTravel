package com.alpha.common.serialize;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alpha.common.utils.IdcardUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/10/22.
 */
@Slf4j
public class IdCardUnSensitiveValueSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
//        log.info("start");
        if (o != null && o instanceof String) {
            String idCard = (String) o;
//            log.info("idCard = {}",idCard);
            jsonSerializer.write(IdcardUtils.unSensitive(idCard,4,2));
        }
    }
}
