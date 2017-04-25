package com.xyl.intelligenttravel.processor;

import com.xyl.intelligenttravel.model.HotelInfo;
import lombok.extern.slf4j.Slf4j;
import webmagic.Page;
import webmagic.Site;
import webmagic.processor.PageProcessor;
import webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店信息processor
 * Created by xueyunlong on 17-4-21.
 */
@Slf4j
public class HotelProcessor implements PageProcessor {
    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        log.info("开始解析酒店信息");

        List<Selectable> nodes = page.getHtml().xpath("//div[@id='hotel_list']//div[@class='searchresult_list searchresult_list2']").nodes();

        List<HotelInfo.Hotel> hotels = new ArrayList<>();
        nodes.forEach(node->{
            Selectable selectable = node.xpath("//ul[@class='searchresult_info']");
            HotelInfo.Hotel hotel = HotelInfo.Hotel.builder()
                    .index(selectable.xpath("//span[@class='hotel_num']//text()").get())
                    .hotelUrl(selectable.xpath("//li[@class='searchresult_info_name']//h2[@class='searchresult_name']").links().get())
                    .name(selectable.xpath("//li[@class='searchresult_info_name']//h2[@class='searchresult_name']//a//text()").get())
                    .address(selectable.xpath("//ul[@class='searchresult_info']").xpath("//li[@class='searchresult_info_name']//p[@class='searchresult_htladdress']//text()").get().replace("【","").replace("】",""))
                    .comments(selectable.xpath("//li[@class='searchresult_info_judge']//span[@class='hotel_judgement']//text()").get())
                    .lastBook(selectable.xpath("//li[@class='searchresult_info_judge']//p[@class='new_book']//text()").get())
                    .score(selectable.xpath("//li[@class='searchresult_info_judge']//span[@class='hotel_value']//text()").get())
                    .price(selectable.xpath("//li[@class='hotel_price_icon']//span[@class='J_price_lowList']//text()").get())
                    .build();
            hotels.add(hotel);
        });

        HotelInfo hotelInfo = HotelInfo.builder()
                .address(page.getRequest().getKey())
                .hotels(hotels)
                .build();
        page.getResultItems().addResult(hotelInfo);

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
