package com.alpha.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * mongodb cient
 * Created by xueyunlong on 17-4-24.
 */
@Service
public class MongodbClient {
    @Autowired
    MongoTemplate mongoTemplate;


    public void saveAll(){

    }

}
