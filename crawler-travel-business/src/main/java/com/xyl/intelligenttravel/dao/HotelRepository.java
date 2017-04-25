package com.xyl.intelligenttravel.dao;

import com.xyl.intelligenttravel.model.HotelInfo;
import com.xyl.intelligenttravel.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * Created by xueyunlong on 17-4-18.
 */
public interface HotelRepository extends MongoRepository<HotelInfo, String> {

}
