package com.alpha.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alpha.common.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import java.io.IOException;

/**
 * Created by chenwen on 16/5/23.
 */
@Slf4j
public class RedissonUtil {
    private static RedissonUtil instance;

    private RedissonClient redisson;

    public RedissonUtil() {
        init();
    }

    public static RedissonUtil getInstance() {
        if (instance == null) {
            synchronized (RedissonUtil.class) {
                if (instance == null) {
                    instance = new RedissonUtil();
                }
            }
        }
        return instance;
    }

    private void init() {
        String jsonConfig = FileUtil.getFromFile("/conf/redission.json");
        try {
            org.redisson.config.Config config = org.redisson.config.Config.fromJSON(JSONObject.parseObject(jsonConfig).getJSONObject("dev").toJSONString());
            redisson = Redisson.create(config);
        } catch (IOException e) {
            log.error("error to read config for redission", e);
        }
    }

    public RedissonClient getRedisson() {
        return instance.redisson;
    }

}


