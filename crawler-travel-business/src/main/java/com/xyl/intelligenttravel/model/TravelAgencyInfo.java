package com.xyl.intelligenttravel.model;

import com.xyl.common.enums.CrawlerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import webmagic.CrawlerResult;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by xueyunlong on 17-4-25.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "travel-agency-info")
public class TravelAgencyInfo implements CrawlerResult {
    private static final long serialVersionUID = 1L;

    /*
    * 景区地址为父类的id
    * */
    @Id
    private String primaryKey;

    private final static Integer infoType = CrawlerTypeEnum.TRAVEL_AGENTCY_CRAWLER.getValue();

    private List<TravelAgency> operatingBest;

    private List<TravelAgency> mostPopular;

    private List<TravelAgency> mostPowerful;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelAgency{

        private String name;

        private String city;

        private String openTime;

        private String score;

        private String picUrl;

        private String url;

        private String index;
    }

}