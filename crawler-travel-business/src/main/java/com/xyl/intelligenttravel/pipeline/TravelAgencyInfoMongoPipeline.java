package com.xyl.intelligenttravel.pipeline;

import com.xyl.intelligenttravel.dao.HotelRepository;
import com.xyl.intelligenttravel.dao.TravelAgencyRepository;
import com.xyl.intelligenttravel.model.HotelInfo;
import com.xyl.intelligenttravel.model.TravelAgencyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import webmagic.ResultItems;
import webmagic.Task;
import webmagic.pipeline.Pipeline;

/**
 *
 * Created by xueyunlong on 17-4-19.
 */
@Service@Slf4j
public class TravelAgencyInfoMongoPipeline implements Pipeline {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TravelAgencyRepository  travelAgencyRepository;
    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("begin save into mongo : {}",resultItems.getResults());
        resultItems.getResults().forEach(crawlerResult -> {
            travelAgencyRepository.save((TravelAgencyInfo) crawlerResult);
        });
        stopWatch.stop();
        log.info("end save into mongo ,costTime = {}",stopWatch.getTotalTimeSeconds());
    }

}
