package com.xyl.intelligenttravel.model;

import lombok.Builder;
import lombok.Data;
import webmagic.CrawlerResult;

/**
 * 景区model
 * Created by xueyunlong on 17-4-18.
 */
@Data@Builder
public class ScenicArea implements CrawlerResult {
    private static final long serialVersionUID = 1L;
    /*
    * 景区名称
    * */
    private String name;
    /*
    * 景区地址
    * */
    private String address;
    /*
    * 景区排名
    * */
    private String ranking;
    /*
    * 景区级别
    * */
    private String level;
    /*
    * 景区评分
    * */
    private String score;
    /*
    * 景区点评量
    * */
    private String comments;
}

