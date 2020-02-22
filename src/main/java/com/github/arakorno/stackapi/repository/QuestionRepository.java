package com.github.arakorno.stackapi.repository;

import com.github.arakorno.stackapi.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, Integer> {
    @Query(value = "{'tags': { $all: ?0 } } ")
    List<Question> findByAllTags(String[] tags);
}
