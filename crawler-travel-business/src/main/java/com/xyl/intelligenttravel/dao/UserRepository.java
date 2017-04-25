package com.xyl.intelligenttravel.dao;

import com.xyl.intelligenttravel.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by xueyunlong on 17-4-18.
 */
public interface UserRepository extends MongoRepository<User, Long> {

}
