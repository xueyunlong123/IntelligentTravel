package webmagic.pipeline;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import webmagic.ResultItems;
import webmagic.Task;

import java.util.Map;

/**
 * Write results in console.<br>
 * Usually used in test.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@Slf4j
public class ConsolePipeline<T> implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
        resultItems.getResults().stream().forEach(result -> {
            log.info("get result: {}", JSONObject.toJSONString(result));
        });
    }

}
