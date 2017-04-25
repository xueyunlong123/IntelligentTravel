package com.xyl.intelligenttravel.model;

import com.xyl.common.enums.CrawlerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import webmagic.CrawlerResult;

import java.util.List;

/**
 * 酒店信息
 * Created by xueyunlong on 17-4-21.
 */
@Data@Builder@AllArgsConstructor@NoArgsConstructor
@Document(collection = "hotel-info")
public class HotelInfo implements CrawlerResult {
    private static final long serialVersionUID = 1L;

    /*
    * 景区地址为父类的id
    * */
    @Id
    private String address;

    private List<Hotel> hotels;

    private final static Integer infoType = CrawlerTypeEnum.HOTEL_CRAWLER.getValue();

    @Data@Builder@AllArgsConstructor@NoArgsConstructor
    public static class Hotel{

        private String name;

        private String index;

        private String address;

        private String comments;

        private String lastBook;

        private String price;

        private String score;

        private String hotelUrl;
    }

}
