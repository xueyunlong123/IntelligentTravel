//package com.xyl.common.web;
//
//import org.apache.commons.lang.time.StopWatch;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.web.SpringBootServletInitializer;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Configuration;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created by chenwen on 16/6/17.
// * web应用入口抽象类
// * 这是入口web应用入口类需要继承此抽象类
// */
//@Slf4j
//@EnableAutoConfiguration
//public abstract class AbstractMainApp extends SpringBootServletInitializer implements ApplicationRunner{
//    @Override
//    public void run(ApplicationArguments var1) throws Exception{
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        log.info("---------------------------- {} start running web with args = {} ----------------------------", this.getClass().getName(), var1);
//
//        start(var1);
//
//        stopWatch.stop();
//        log.info("---------------------------- end running web  costTime = {} (ms) ----------------------------", stopWatch.getTime());
//    }
//
//    public void start(ApplicationArguments arguments) {
//    }
//
//    protected static ApplicationContext context;
//
//    public static ApplicationContext getContext() {
//        return context;
//    }
//
//
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(this.getClass());
//    }
//}
