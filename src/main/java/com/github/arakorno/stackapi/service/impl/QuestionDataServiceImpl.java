package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.entity.Question;
import com.github.arakorno.stackapi.exception.QuestionNotFoundException;
import com.github.arakorno.stackapi.model.QuestionModel;
import com.github.arakorno.stackapi.repository.QuestionRepository;
import com.github.arakorno.stackapi.service.QuestionService;
import com.github.arakorno.stackapi.service.StackexchangeApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionDataServiceImpl implements QuestionService {
    private final StackexchangeApiService stackexchangeApiService;
    private final QuestionRepository questionRepository;

    @PostConstruct
    public void populateDataSource() {
        questionRepository.deleteAll();
        QuestionModel data = stackexchangeApiService.getQuestions();
        List<Question> questions = Question.of(data);
        questionRepository.saveAll(questions);
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestion(Integer id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id " + id + " not found!"));
    }

    @Override
    public List<Question> findQuestionsByTags(String[] tags) {
        return questionRepository.findByTags(tags);
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }
}
