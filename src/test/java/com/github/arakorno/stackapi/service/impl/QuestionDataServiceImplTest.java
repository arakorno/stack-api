package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.entity.Question;
import com.github.arakorno.stackapi.repository.QuestionRepository;
import com.github.arakorno.stackapi.service.StackexchangeApiService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionDataServiceImplTest {
    @InjectMocks
    private QuestionDataServiceImpl questionDataService;
    @Mock
    private StackexchangeApiService stackexchangeApiService;
    @Mock
    private QuestionRepository questionRepository;
    private List<Question> questions = new ArrayList<>();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Question q1 = Question.builder().id(1).tags(List.of("java", "mongo")).build();
        Question q2 = Question.builder().id(2).tags(List.of("spring", "mongo", "java")).build();
        Question q3 = Question.builder().id(3).tags(List.of("python", "java")).build();
        Question q4 = Question.builder().id(4).tags(List.of("json", "rest")).build();
        questions.addAll(List.of(q1, q2, q3, q4));
    }

    @Test
    public void getQuestions() {
        when(questionRepository.findAll()).thenReturn(questions);
        List<Question> res = questionDataService.getQuestions();
        Assert.assertEquals(4, res.size());
    }

    @Test
    public void getQuestion() {
        when(questionRepository.findById(1)).thenReturn(Optional.of(questions.get(0)));
        Question res = questionDataService.getQuestion(1);
        Assert.assertEquals("mongo", res.getTags().get(1));
    }

    @Test
    public void findQuestionsByTags() {
        when(questionRepository.findByTags(List.of("java", "python").toArray(String[]::new)))
                .thenReturn(List.of(questions.get(2)));
        List<Question> res = questionRepository.findByTags(List.of("java", "python").toArray(String[]::new));
        Assert.assertEquals(3, res.get(0).getId().intValue());
    }

    @Test
    public void deleteQuestion() {
        doNothing().when(questionRepository).deleteById(1);
        questionDataService.deleteQuestion(1);
    }
}