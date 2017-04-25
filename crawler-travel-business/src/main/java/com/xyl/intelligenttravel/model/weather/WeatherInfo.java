package com.xyl.intelligenttravel.model.weather;

import com.xyl.common.enums.CrawlerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.MongoModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import webmagic.CrawlerResult;
import java.util.Date;
import java.util.List;

/**
 * 天气信息
 * Created by xueyunlong on 17-4-19.
 */
@Data@Builder@AllArgsConstructor@NoArgsConstructor
@Document(collection = "weather-info")
public class WeatherInfo implements CrawlerResult {

    private static final long serialVersionUID = 1L;

    @Id
    private String address;

    private String url;

    private List<Weather> weathers;

    private final static Integer infoType = CrawlerTypeEnum.WEATHER_CRAWLER.getValue();

    private Date crawlerTime;

    @Data@Builder@AllArgsConstructor@NoArgsConstructor
    public static class Weather{

        /*
        * 日期
        * */
        private String date;

        /*
        * 温度
        * */
        private String temperature;

        /*
        * 描述：晴转多云
        * */
        private String description;

        /*
        * 图片地址
        * */
        private String imgUrl;
    }
}
