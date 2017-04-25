package com.xyl.intelligenttravel.buiness.impl;

import com.xyl.common.enums.CrawlerTypeEnum;
import com.xyl.intelligenttravel.buiness.Dispatcher;
import com.xyl.intelligenttravel.pipeline.RedisListPipeline;
import com.xyl.intelligenttravel.pipeline.RedisMapPipeline;
import com.xyl.intelligenttravel.processor.TravelAgencyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webmagic.Request;
import webmagic.Spider;
import webmagic.utils.HttpConstant;

/**
 * 查询旅行社调度
 * Created by xueyunlong on 17-4-21.
 */
@Service
public class TravelAgencyDispather implements Dispatcher {

    @Autowired
    RedisListPipeline redisListPipeline;
    /**
     * dispatch your buseiness with t
     */
    @Override
    public void dispatch() {
        Spider.create(new TravelAgencyProcessor())
                .addRequest(
                        Request.builder()
                                .key(CrawlerTypeEnum.TRAVEL_AGENTCY_CRAWLER.toString())
                                .baseIntEnum(CrawlerTypeEnum.TRAVEL_AGENTCY_CRAWLER)
                                .url("http://lxs.cncn.com/top")
                                .method(HttpConstant.Method.GET)
                                .build())
                .addPipeline(redisListPipeline)
                .run();
    }

    @Override
    public void dispatch(String key) {

    }


    public static void main(String[] args) {
        new TravelAgencyDispather().dispatch();
    }
}
