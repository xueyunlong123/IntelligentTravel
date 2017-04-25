package com.xyl.intelligenttravel.buiness.impl;

import com.alibaba.fastjson.JSONObject;
import com.xyl.common.enums.CrawlerTypeEnum;
import com.xyl.intelligenttravel.buiness.Dispatcher;
import com.xyl.intelligenttravel.pipeline.RedisMapPipeline;
import com.xyl.intelligenttravel.pipeline.WeatherInfoMongoPipeline;
import com.xyl.intelligenttravel.processor.WeatherPageProcesoor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webmagic.Request;
import webmagic.Spider;
import webmagic.utils.HttpConstant;
import java.io.IOException;

/**
 * 天气查询
 * Created by xueyunlong on 17-4-19.
 */
@Service @Builder @Slf4j
public class WeatherDispatcher implements Dispatcher {

    @Autowired
    WeatherInfoMongoPipeline mongoPipeline;
    @Autowired
    RedisMapPipeline redisMapPipeline;


    /**
     * dispatch your buseiness
     *
     */
    @Override
    public void dispatch() {
    }

    public void dispatch(String address){
        JSONObject area = null;
        try {
            area = JSONObject.parseObject(IOUtils.toString(WeatherDispatcher.class.getClassLoader().getResourceAsStream("conf/area.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Request request = Request.builder()
                .baseIntEnum(CrawlerTypeEnum.WEATHER_CRAWLER)
                .key(address)
                .method(HttpConstant.Method.GET)
                .url("http://tianqi.cncn.com/city.php?city_id="+ (area != null ? area.getString(address) : null))
                .build();
        request.addExtra("address",address);
        Spider
                .create(new WeatherPageProcesoor())
                .addPipeline(mongoPipeline)
                .addPipeline(redisMapPipeline)
                .addRequest(request)
                .run();
        log.info("天气处理完成");
    }

    public static void main(String[] args) {
        WeatherDispatcher.builder().build().dispatch("北京");
    }
}
