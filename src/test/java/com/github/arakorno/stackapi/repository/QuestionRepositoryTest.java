package com.github.arakorno.stackapi.repository;

import com.github.arakorno.stackapi.entity.Question;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByTags() {
        Question q1 = Question.builder().id(1).tags(List.of("java", "mongo")).build();
        Question q2 = Question.builder().id(2).tags(List.of("spring", "mongo", "java")).build();
        Question q3 = Question.builder().id(3).tags(List.of("python", "java")).build();
        Question q4 = Question.builder().id(4).tags(List.of("json", "rest")).build();
        questionRepository.saveAll(List.of(q1, q2, q3, q4));
        List<Question> res1 = questionRepository.findByTags(new String[] { "java" });
        Assert.assertEquals(3, res1.size());
        List<Question> res2 = questionRepository.findByTags(new String[] { "mongo", "java" });
        Assert.assertEquals(2, res2.size());
        List<Question> res3 = questionRepository.findByTags(new String[] { "java", "mongo", "spring" });
        Assert.assertEquals(1, res3.size());
    }
}