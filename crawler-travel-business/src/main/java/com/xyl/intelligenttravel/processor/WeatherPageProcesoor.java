package com.xyl.intelligenttravel.processor;

import com.xyl.intelligenttravel.model.weather.WeatherInfo;
import lombok.extern.slf4j.Slf4j;
import webmagic.Page;
import webmagic.Site;
import webmagic.processor.PageProcessor;
import webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 天气页面处理者
 * Created by xueyunlong on 17-4-19.
 */
@Slf4j
public class WeatherPageProcesoor implements PageProcessor {
    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        log.info("开始处理天气");

        WeatherInfo weatherInfo = WeatherInfo.builder()
                .address(page.getRequest().getKey())
                .url(page.getRequest().getUrl())
                .crawlerTime(new Date())
                .build();
        List<WeatherInfo.Weather> weathers = new ArrayList<>();
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='w_city']//li").nodes();
        nodes.forEach(node->{
            WeatherInfo.Weather weather = WeatherInfo.Weather.builder()
                    .date(node.xpath("//span//text()").get().replace("(","").replace(")",""))
                    .imgUrl(node.xpath("//p[@class='w_pic']").$("img","src").get())
                    .description(node.xpath("//p//a//text()").get())
                    .temperature(node.xpath("//p//text()").nodes().get(2).get())
                    .build();
            weathers.add(weather);
        });
        weatherInfo.setWeathers(weathers);

        page.getResultItems().addResult(weatherInfo);
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
