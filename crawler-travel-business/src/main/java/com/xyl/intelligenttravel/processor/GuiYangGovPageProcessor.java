package com.xyl.intelligenttravel.processor;

import com.xyl.intelligenttravel.model.ScenicArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webmagic.Page;
import webmagic.Site;
import webmagic.processor.PageProcessor;
import webmagic.selector.Selectable;
import java.util.List;

/**
 * 贵阳景区携程网处理逻辑
 * Created by xueyunlong on 17-4-18.
 */
@Slf4j
@Service
public class GuiYangGovPageProcessor implements PageProcessor{

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {

        List<Selectable> nodes =  page.getHtml().xpath("//div[@class='list_wide_mod2']//div[@class='list_mod2']").nodes();
        for (Selectable node : nodes) {
            ScenicArea scenicArea = ScenicArea.builder()
                    .name(node.xpath("//div[@class='rdetailbox']//dt//a//text()").get())
                    .ranking(node.xpath("//div[@class='rdetailbox']//dt//s//text()").get())
                    .address(node.xpath("//div[@class='rdetailbox']//dd//text()").get())
                    .level(node.xpath("//div[@class='rdetailbox']//dd//text()").nodes().get(1).get())
                    .score(node.xpath("//div[@class='rdetailbox']//ul//a[@class='score']//strong//text()").get())
                    .comments(node.xpath("//div[@class='rdetailbox']//ul//a[@rel='nofollow']//text()").get().replace("(","").replace(")",""))
                    .build();
            page.getResultItems().addResult(scenicArea);
        }
        String nextUrl = page.getHtml().xpath("//a[@class='nextpage']").links().get();
        page.addTargetRequest(nextUrl);
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
