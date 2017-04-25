package com.xyl.intelligenttravel.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import webmagic.ResultItems;
import webmagic.Task;
import webmagic.pipeline.Pipeline;

/**
 * redis pipeline
 * Created by xueyunlong on 17-4-19.
 */
@Service@Slf4j
public class RedisListPipeline implements Pipeline {


    @Autowired
    private RedissonClient redissonClient;

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
        log.info("begin cache into redis : {}",resultItems.getResults());
        resultItems.getResults().forEach(crawlerResult -> {
            redissonClient.getList(resultItems.getRequest().getKey()).add(crawlerResult);
        });
        stopWatch.stop();
        log.info("end cache into redis ,costTime = {}",stopWatch.getTotalTimeSeconds());
    }

}
