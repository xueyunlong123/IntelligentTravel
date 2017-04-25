package com.xyl.intelligenttravel.controller;

import com.alibaba.fastjson.JSONObject;
import com.alpha.common.utils.JacksonUtil;
import com.xyl.common.enums.CrawlerTypeEnum;
import com.xyl.common.web.fastjson.FastJson;
import com.xyl.common.web.result.JSONResult;
import com.xyl.common.web.result.Result;
import com.xyl.intelligenttravel.buiness.impl.HotelDispather;
import com.xyl.intelligenttravel.buiness.impl.ScenicDispatcher;
import com.xyl.intelligenttravel.buiness.impl.TravelAgencyDispather;
import com.xyl.intelligenttravel.buiness.impl.WeatherDispatcher;
import com.xyl.intelligenttravel.dto.CrawlerRequestDTO;
import com.xyl.intelligenttravel.service.CrawlerTravelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import webmagic.CrawlerResult;

import java.util.List;

/**
 * 旅游信息爬虫controller
 * Created by xueyunlong on 17-4-19.
 */
@Slf4j
@RestController
@RequestMapping("/travel_crawler")
public class CrawlerTravelController {

    @Autowired
    private ScenicDispatcher gyGovTask;

    @Autowired private WeatherDispatcher weatherDispatcher;
    @Autowired private HotelDispather hotelDispather;
    @Autowired private TravelAgencyDispather travelAgencyDispather;
    @Autowired
    private
    CrawlerTravelService crawlerTravelService;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 健康检测
     * @return string
     *
     */
    @RequestMapping(value = "/health",method = RequestMethod.GET)
    public String home(){
        return "health";
    }

    /*
    * 爬虫同步请求天气信息
    * */

    @RequestMapping(value = "/sync_crawler/weather_info", method = RequestMethod.POST)
    @ApiOperation(value = "爬虫同步请求天气信息",notes = "",code = 200 ,produces = "application/json")
    public Result syncWeatherInfoCrawler(@FastJson @Validated CrawlerRequestDTO crawlerRequestDTO){
        weatherDispatcher.dispatch(crawlerRequestDTO.getAddress());
        /*
        * 超时时间内完成
        * */

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        FutureTask futureTask = new FutureTask(() -> {
//
//            //重试60次
//            int retryCount = 1;
//            CrawlerResult crawlerResult = null;
//            while(retryCount <= 60){
//                log.info("获取爬虫结果第{}次",retryCount);
//                crawlerResult = (CrawlerResult) redissonClient.getMapCache(CrawlerTypeEnum.WEATHER_CRAWLER.toString()).get(crawlerRequestDTO.getAddress());
//                Thread.sleep(5000);
//                if (crawlerResult != null){
//                    return crawlerResult;
//                }
//                retryCount++;
//            }
//            return null;
//        });
//        executorService.execute(futureTask);

        CrawlerResult crawlerResult = null;
        crawlerResult = (CrawlerResult) redissonClient.getMapCache(CrawlerTypeEnum.WEATHER_CRAWLER.toString()).get(crawlerRequestDTO.getAddress());
//        try {
//            //3分钟后超时
//            crawlerResult = (CrawlerResult) futureTask.get(3, TimeUnit.MINUTES);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//        }
//        if (crawlerResult == null) {
//            throw new BaseException(BasicErrorCodeEnum.GET_CRAWLER_RESULT_FAILURE_E000012);
//        }
        return JSONResult.ok(crawlerResult);
    }

    /*
    * 爬虫同步请求酒店信息
    * */

    @RequestMapping(value = "/sync_crawler/hotel_info", method = RequestMethod.POST)
    @ApiOperation(value = "爬虫同步请求酒店信息",notes = "",code = 200 ,produces = "application/json")
    public Result syncHotelInfoCrawler(@FastJson @Validated CrawlerRequestDTO crawlerRequestDTO){
        hotelDispather.dispatch(crawlerRequestDTO.getAddress());
        CrawlerResult crawlerResult = (CrawlerResult) redissonClient.getMapCache(CrawlerTypeEnum.HOTEL_CRAWLER.toString()).get(crawlerRequestDTO.getAddress());
        return JSONResult.ok(crawlerResult);
    }
    /*
    * 爬虫同步请求旅行社信息
    * */

    @RequestMapping(value = "/sync_crawler/travel_agency_info", method = RequestMethod.GET)
    @ApiOperation(value = "爬虫同步请求旅行社信息",notes = "",code = 200 ,produces = "application/json")
    public Result syncTravelAgencyInfoCrawler(){
        travelAgencyDispather.dispatch();
        List<CrawlerResult> crawlerResults = redissonClient.getList(CrawlerTypeEnum.TRAVEL_AGENTCY_CRAWLER.toString());
        JSONObject data = JacksonUtil.objToJSONObject(crawlerResults.get(0));
        crawlerResults.clear();
        return JSONResult.ok(data);
    }
    /**
     * 爬虫异步请求
     */
    @ApiOperation(value = "异步爬虫调用接口")
    @RequestMapping(value = "/asyn_crawler",method = RequestMethod.GET)
    public void asynCrawler(){
        gyGovTask.dispatch();
    }

    /*
    * 数据查询
    * */
    @RequestMapping(value = "data_query",method = RequestMethod.POST)
    @ApiOperation(value = "数据查询接口",notes = "根据相应主键查询数据",code = 200 ,produces = "application/json")
    @ApiResponse(code = 200,message = "success",response = Result.class)
    public Result dataQuery(@FastJson CrawlerRequestDTO requestDTO){
        CrawlerResult result = crawlerTravelService.getWeatherResultByField("address",requestDTO.getAddress());
        log.info("获取数据为：{}",result);
        CrawlerResult result1 = crawlerTravelService.getResultByIdAndInfoType("address",requestDTO.getAddress(),CrawlerTypeEnum.WEATHER_CRAWLER.getValue());
        return JSONResult.ok(result);
    }
}
