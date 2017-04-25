package com.xyl.intelligenttravel.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 爬虫请求dto
 * Created by xueyunlong on 17-4-20.
 */
@Data@Builder@AllArgsConstructor@NoArgsConstructor
public class CrawlerRequestDTO {

//    /*
//    * 具体请求爬虫的类型
//    * */
//    @JSONField(name = "crawler_type")
//    private int crawlerType;

    @NotEmpty
    @JSONField(name = "address")
    private String address;
}
