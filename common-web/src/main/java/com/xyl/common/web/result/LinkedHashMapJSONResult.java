package com.xyl.common.web.result;

import com.alibaba.fastjson.JSONObject;
import com.xyl.common.enums.IErrorCode;

/**
 * Created by xueyunlong on 17-3-4.
 */
public class LinkedHashMapJSONResult extends Result<JSONObject>{
    private LinkedHashMapJSONResult(IErrorCode iErrorCode, boolean success, JSONObject data){
        this(iErrorCode.getCode(), iErrorCode.getMessage(),success,data);
    }

    private LinkedHashMapJSONResult(String code, String message, boolean success, JSONObject data) {
        super.setCode(code);
        super.setMessage(message);
        super.setSuccess(success);
        super.setData(data);
    }

    public static LinkedHashMapJSONResult ok(JSONObject object) {
        return new LinkedHashMapJSONResult(IErrorCode.OK, true, object);
    }


}