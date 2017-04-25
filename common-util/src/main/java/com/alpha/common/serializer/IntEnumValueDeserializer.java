package com.alpha.common.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.xyl.common.enums.BaseIntEnum;
import com.alpha.common.utils.CommonUtil;

import java.lang.reflect.Type;

/**
 * Created by chenwen on 16/10/28.
 */
public class IntEnumValueDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
//        Integer value = null;
//       try{
//           value = defaultJSONParser.getLexer().integerValue() == null ? Integer.parseInt(defaultJSONParser.getLexer().stringVal()) : defaultJSONParser.getLexer().intValue();
//       }catch (Exception e){
//           value = Integer.parseInt(defaultJSONParser.getLexer().stringVal());
//       }

        BaseIntEnum[] enumValArr = (BaseIntEnum[]) ((Class) type).getEnumConstants();
        for (BaseIntEnum enumVal : enumValArr) {
            if (enumVal.getValue() == defaultJSONParser.getLexer().intValue()) {
                return (T) enumVal;
            }
        }

        JSONObject json = JSON.parseObject(defaultJSONParser.getInput());
        if (json.containsKey(CommonUtil.to_(o.toString()))) {
            int value = json.getIntValue(CommonUtil.to_(o.toString()));
            for (BaseIntEnum enumVal : enumValArr) {
                if (enumVal.getValue() == value) {
                    return (T) enumVal;
                }
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
