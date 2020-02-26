package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.exception.StackexchangeApiException;
import com.github.arakorno.stackapi.model.QuestionModel;
import com.github.arakorno.stackapi.model.UserModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static com.github.arakorno.stackapi.service.StackexchangeApiService.API_ERROR_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
    }

    @Test
    public void testGetQuestions_failed() {
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(QuestionModel.builder().build());
        exception.expect(StackexchangeApiException.class);
        exception.expectMessage(API_ERROR_MESSAGE);
        service.getQuestions();
    }

    @Test
    public void testGetUsers() {
        UserModel response = UserModel.builder().userItems(List.of(UserModel.UserItem.builder().userId(1)
                .displayName("superuser").creationDate("2008-07-31T17:22:31+0300").build())).build();
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(response);
        UserModel res = service.getUsers(1);
        Assert.assertEquals("superuser", res.getUserItems().get(0).getDisplayName());
    }

    @Test
    public void testGetUsers_failed() {
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(UserModel.builder().build());
        exception.expect(StackexchangeApiException.class);
        exception.expectMessage(API_ERROR_MESSAGE);
        service.getUsers(1);
    }
}
