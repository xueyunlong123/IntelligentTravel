package com.xyl.intelligenttravel.dao;

import com.xyl.intelligenttravel.model.HotelInfo;
import com.xyl.intelligenttravel.model.TravelAgencyInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * Created by xueyunlong on 17-4-18.
 */
public interface TravelAgencyRepository extends MongoRepository<TravelAgencyInfo, String> {

}
