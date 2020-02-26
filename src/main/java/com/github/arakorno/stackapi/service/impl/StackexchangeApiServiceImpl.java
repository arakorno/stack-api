package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.exception.StackexchangeApiException;
import com.github.arakorno.stackapi.model.QuestionModel;
import com.github.arakorno.stackapi.model.UserModel;
import com.github.arakorno.stackapi.service.StackexchangeApiService;
import com.github.arakorno.stackapi.utill.JsonLogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
@RequiredArgsConstructor
public class StackexchangeApiServiceImpl implements StackexchangeApiService {
    @Value("${api.stackexchange.url}")
    private String apiStackexchangeUrl;
    private final RestTemplate restTemplate;

    @Override
    public QuestionModel getQuestions() {
        URI uri = fromHttpUrl(apiStackexchangeUrl + QUESTION_URL).queryParam("page", 1).queryParam("pagesize", 20)
                .queryParam("order", "desc").queryParam("sort", "creation").queryParam("site", "stackoverflow").build()
                .toUri();
        return Optional
                .ofNullable(restTemplate
                        .exchange(uri, HttpMethod.GET, new HttpEntity(getHeaders()), QuestionModel.class).getBody())
                .map(JsonLogUtils::logJson).filter(c -> Objects.nonNull(c.getQuestionItems()))
                .orElseThrow(() -> new StackexchangeApiException(API_ERROR_MESSAGE));
    }

    @Override
    public UserModel getUsers(Integer userId) {
        URI uri = fromHttpUrl(apiStackexchangeUrl + USER_DETAILS_URL).queryParam("site", "stackoverflow")
                .buildAndExpand(userId).toUri();
        return Optional
                .ofNullable(restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity(getHeaders()), UserModel.class)
                        .getBody())
                .map(JsonLogUtils::logJson).filter(c -> Objects.nonNull(c.getUserItems()))
                .orElseThrow(() -> new StackexchangeApiException(API_ERROR_MESSAGE));
    }
}
