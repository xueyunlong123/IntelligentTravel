package com.xyl.intelligenttravel.swagger;


import com.xyl.intelligenttravel.controller.CrawlerTravelController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * Created by xueyunlong on 17-4-21.
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
        CrawlerTravelController.class
})
public class Swagger2SpringBoot {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Swagger2SpringBoot.class, args);
    }


    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("test")
//                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
//                .pathMapping("/")//api测试请求地址
                .select()
                .paths(PathSelectors.regex("/travel_crawler/.*"))//过滤的接口
                .build()
                .apiInfo(testApiInfo());
    }

    private ApiInfo testApiInfo() {
        Contact contact = new Contact("薛云龙", "http://www.jianshu.com/u/9b9b7abc1060", "976359221@qq.com");
        ApiInfo apiInfo = new ApiInfo("智能旅行爬虫接口",//大标题
                "REST风格API",//小标题
                "0.1",//版本
                "www.baidu.com",
                contact,//作者
                "主页",//链接显示文字
                "http://k8e7ws.natappfree.cc/travel_crawler/data_query/%E5%8C%97%E4%BA%AC"//网站链接
        );
        return apiInfo;
    }

}
