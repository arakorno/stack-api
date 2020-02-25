package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.exception.InternalServerException;
import com.github.arakorno.stackapi.model.QuestionModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class StackexchangeApiServiceImplTest {
    @InjectMocks
    private StackexchangeApiServiceImpl service;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ResponseEntity responseEntity;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        ReflectionTestUtils.setField(service, "apiStackexchangeUrl", "http://api.stack.com");
    }

    @Test
    public void testGetQuestions() {
        QuestionModel response = QuestionModel.builder()
                .questionItems(List
                        .of(QuestionModel.QuestionItem.builder().tags(List.of("java", "mongo")).answerCount(5).build()))
                .build();
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(response);
        QuestionModel res = service.getQuestions();
        Assert.assertEquals(res.getQuestionItems().get(0).getTags().size(), 2);

        when(responseEntity.getBody()).thenReturn(QuestionModel.builder().build());
        exception.expect(InternalServerException.class);
        exception.expectMessage("Failed to get data");
        service.getQuestions();
    }
}
