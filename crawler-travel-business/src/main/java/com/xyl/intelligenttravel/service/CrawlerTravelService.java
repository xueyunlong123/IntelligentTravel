package com.xyl.intelligenttravel.service;

import webmagic.CrawlerResult;

/**
 * 智能旅行service 接口
 * Created by xueyunlong on 17-4-20.
 */
public interface CrawlerTravelService {

    CrawlerResult getWeatherResultByField(String field, String primaryKey);

    CrawlerResult getResultByIdAndInfoType(String field, String primaryKey, Integer infoType);
}
