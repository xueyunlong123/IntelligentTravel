package com.xyl.intelligenttravel.processor;

import com.xyl.common.enums.CrawlerTypeEnum;
import com.xyl.intelligenttravel.model.HotelInfo;
import com.xyl.intelligenttravel.model.TravelAgencyInfo;
import lombok.extern.slf4j.Slf4j;
import webmagic.Page;
import webmagic.Site;
import webmagic.processor.PageProcessor;
import webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅行社信息processor
 * Created by xueyunlong on 17-4-21.
 */
@Slf4j
public class TravelAgencyProcessor implements PageProcessor {
    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        log.info("开始解析旅行社信息");
        List<TravelAgencyInfo.TravelAgency> operatingBest = new ArrayList<>();
        List<TravelAgencyInfo.TravelAgency> mostPopular = new ArrayList<>();
        List<TravelAgencyInfo.TravelAgency> mostPowerful = new ArrayList<>();
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='top1000']").nodes();
        nodes.get(0).xpath("//dl").nodes().forEach(node->{
            log.info("处理经营最佳旅行社");
            TravelAgencyInfo.TravelAgency travelAgency = TravelAgencyInfo.TravelAgency.builder()
                    .city(node.xpath("//span").nodes().get(0).xpath("//a//text()").get())
                    .openTime(node.xpath("//span").nodes().get(1).xpath("//span//text()").get().replace("开店时间：",""))
                    .name(node.xpath("//strong//a//text()").get())
                    .url(node.xpath("//strong").css("a","href").get())
                    .score(node.xpath("//em//text()").get())
                    .picUrl(node.css("img","src").get())
                    .build();
            operatingBest.add(travelAgency);
        });
        nodes.get(1).xpath("//dl").nodes().forEach(node->{
            log.info("处理最具人气旅行社");
            TravelAgencyInfo.TravelAgency travelAgency = TravelAgencyInfo.TravelAgency.builder()
                    .city(node.xpath("//span").nodes().get(0).xpath("//a//text()").get())
                    .openTime(node.xpath("//span").nodes().get(1).xpath("//span//text()").get().replace("开店时间：",""))
                    .name(node.xpath("//strong//a//text()").get())
                    .url(node.xpath("//strong").css("a","href").get())
                    .score(node.xpath("//em//text()").get())
                    .picUrl(node.css("img","src").get())
                    .build();
            mostPopular.add(travelAgency);
        });
        nodes.get(2).xpath("//dl").nodes().forEach(node->{
            log.info("处理最具实力旅行社");
            TravelAgencyInfo.TravelAgency travelAgency = TravelAgencyInfo.TravelAgency.builder()
                    .city(node.xpath("//span").nodes().get(0).xpath("//a//text()").get())
                    .openTime(node.xpath("//span").nodes().get(1).xpath("//span//text()").get().replace("开店时间：",""))
                    .name(node.xpath("//strong//a//text()").get())
                    .url(node.xpath("//strong").css("a","href").get())
                    .score(node.xpath("//em//text()").get())
                    .picUrl(node.css("img","src").get())
                    .build();
            mostPowerful.add(travelAgency);
        });

        TravelAgencyInfo travelAgencyInfo = TravelAgencyInfo.builder()
                .mostPopular(mostPopular)
                .mostPowerful(mostPowerful)
                .operatingBest(operatingBest)
                .primaryKey(CrawlerTypeEnum.TRAVEL_AGENTCY_CRAWLER.toString())
                .build();

        page.getResultItems().addResult(travelAgencyInfo);

    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return null;
    }
}
