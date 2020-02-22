package com.github.arakorno.stackapi.repository;

import com.github.arakorno.stackapi.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, Integer> {
}
