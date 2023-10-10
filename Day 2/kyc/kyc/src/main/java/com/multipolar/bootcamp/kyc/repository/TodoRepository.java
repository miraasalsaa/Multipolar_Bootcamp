package com.multipolar.bootcamp.kyc.repository;

import com.multipolar.bootcamp.kyc.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo,String> {

}
