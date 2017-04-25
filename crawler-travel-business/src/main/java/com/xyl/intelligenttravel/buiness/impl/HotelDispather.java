package com.xyl.intelligenttravel.buiness.impl;

import com.xyl.common.enums.CrawlerTypeEnum;
import com.xyl.intelligenttravel.buiness.Dispatcher;
import com.xyl.intelligenttravel.pipeline.HotelInfoMongoPipeline;
import com.xyl.intelligenttravel.pipeline.RedisMapPipeline;
import com.xyl.intelligenttravel.processor.HotelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webmagic.Request;
import webmagic.Spider;
import webmagic.utils.HttpConstant;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 查询酒店调度
 * Created by xueyunlong on 17-4-21.
 */
@Service
public class HotelDispather implements Dispatcher {

    @Autowired
    HotelInfoMongoPipeline hotelInfoMongoPipeline;

    @Autowired
    RedisMapPipeline redisMapPipeline;
    /**
     * dispatch your buseiness with t
     */
    @Override
    public void dispatch() {}

    @Override
    public void dispatch(String key) {
        try {
            Spider.create(new HotelProcessor())
                    .addRequest(
                            Request.builder()
                                    .key(key)
                                    .baseIntEnum(CrawlerTypeEnum.HOTEL_CRAWLER)
                                    .url("http://hotels.ctrip.com/hotel/guiyang38/"+ URLEncoder.encode(key,"UTF-8"))
                                    .method(HttpConstant.Method.GET)
                                    .build())
                    .addPipeline(hotelInfoMongoPipeline)
                    .addPipeline(redisMapPipeline)
                    .run();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HotelDispather().dispatch("云岩");
    }
}
