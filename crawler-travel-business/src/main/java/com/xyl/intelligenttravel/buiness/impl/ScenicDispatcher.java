package com.xyl.intelligenttravel.buiness.impl;

import com.xyl.intelligenttravel.buiness.Dispatcher;
import com.xyl.intelligenttravel.processor.GuiYangGovPageProcessor;
import org.springframework.stereotype.Service;
import webmagic.Request;
import webmagic.Spider;
import webmagic.pipeline.ConsolePipeline;
import webmagic.utils.HttpConstant;


/**
 * Created by xueyunlong on 17-4-18.
 */
@Service
public class ScenicDispatcher implements Dispatcher {

    /**
     * dispatch your buseiness with t
     *
     */
    @Override
    public void dispatch() {
        Spider
                .create(new GuiYangGovPageProcessor())
                .addRequest(
                        Request.builder()
                                .method(HttpConstant.Method.GET)
                                .url("http://you.ctrip.com/sight/guiyang33/s0-p1.html")
                                .build()
                )
                .addPipeline(new ConsolePipeline())
                .run();
    }

    @Override
    public void dispatch(String key) {

    }
}
