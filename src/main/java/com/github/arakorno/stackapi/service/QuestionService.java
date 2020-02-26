package com.github.arakorno.stackapi.service;

import com.github.arakorno.stackapi.entity.Question;

import javax.annotation.PostConstruct;
import java.util.List;

public interface QuestionService {
    @PostConstruct
    void populateDataSource();

    List<Question> getQuestions();

    Question getQuestion(Integer id);

    List<Question> findQuestionsByTags(String[] tags);

    void deleteQuestion(Integer id);
}
