package com.alpha.common.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alpha.common.utils.SystemAmountUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Created by chenwen on 16/10/28.
 */
public class BigDecimalValueDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
//        JSONObject json = JSON.parseObject(defaultJSONParser.getInput());
//        if (json.containsKey(o.toString()) || json.containsKey(CommonUtil.to_(o.toString()))) {
//            String value;
//            if (json.containsKey(o.toString())) {
//                value = json.getString(o.toString());
//            } else {
//                value = json.getString(CommonUtil.to_(o.toString()));
//            }
//            return (T) new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
//        }
        BigDecimal amount = null;

        try{
            amount = defaultJSONParser.getLexer().decimalValue();
        }catch (Exception e){
            amount = new BigDecimal(defaultJSONParser.getLexer().stringVal());
        }

        return (T) amount.setScale(SystemAmountUtil.amountScale, SystemAmountUtil.amountMath);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
