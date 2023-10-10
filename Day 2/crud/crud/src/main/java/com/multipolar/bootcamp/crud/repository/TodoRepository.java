package com.multipolar.bootcamp.crud.repository;

import com.multipolar.bootcamp.crud.domain.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {

}
