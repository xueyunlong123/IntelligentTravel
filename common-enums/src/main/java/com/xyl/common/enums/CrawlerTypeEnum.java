package com.xyl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 爬虫类型枚举
 * Created by xueyunlong on 17-4-21.
 */
public enum CrawlerTypeEnum implements BaseIntEnum {

    WEATHER_CRAWLER(1,"天气信息"),
    HOTEL_CRAWLER(2,"酒店信息"),
    TRAVEL_AGENTCY_CRAWLER(3,"旅行社信息"),


    ;

    @Getter
    private final int value;
    @Getter
    private final String name;

    CrawlerTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
