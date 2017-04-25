package com.xyl.intelligenttravel.service.impl;

import com.xyl.common.enums.CrawlerTypeEnum;
import com.xyl.intelligenttravel.model.HotelInfo;
import com.xyl.intelligenttravel.model.weather.WeatherInfo;
import com.xyl.intelligenttravel.service.CrawlerTravelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import webmagic.CrawlerResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 智能旅行service 实现
 * Created by xueyunlong on 17-4-20.
 */
@Slf4j
@Service
public class CrawlerTravelServiceImpl implements CrawlerTravelService {

    private Map<Integer,Class> crawlerTypeEnumClassMap = new HashMap(){{
        put(CrawlerTypeEnum.WEATHER_CRAWLER.getValue(),WeatherInfo.class);
        put(CrawlerTypeEnum.HOTEL_CRAWLER.getValue(), HotelInfo.class);
    }};

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public CrawlerResult getWeatherResultByField(String field, String primaryKey) {
        log.info("weatherinfo key is :{}",primaryKey);
        log.info("field is :{}",field);
        Query query = Query.query(Criteria.where(field).is(primaryKey));
        WeatherInfo weatherInfo = mongoTemplate.findOne(query, WeatherInfo.class);
        log.info("weatherinfo is :{}",weatherInfo);
        return weatherInfo;
    }

    @Override
    public CrawlerResult getResultByIdAndInfoType(String field, String primaryKey, Integer infoType) {
        log.info("field is :{}",field);
        log.info("primaryKey is {}",primaryKey);
        log.info("infoType is {}",infoType);

        Query query = Query.query(Criteria.where(field).is(primaryKey));
        Object o = null;
        try {
            o = crawlerTypeEnumClassMap.get(infoType).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        o = mongoTemplate.findOne(query, crawlerTypeEnumClassMap.get(infoType));

        return (CrawlerResult) o;
    }
}
