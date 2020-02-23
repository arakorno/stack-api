package com.github.arakorno.stackapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arakorno.stackapi.exception.InternalServerException;
import com.github.arakorno.stackapi.model.QuestionModel;
import com.github.arakorno.stackapi.service.QuestionApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Log4j2
@Service
@RequiredArgsConstructor
public class QuestionApiServiceImpl implements QuestionApiService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
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
                .map(this::logJson).filter(c -> Objects.nonNull(c.getItems()))
                .orElseThrow(() -> new InternalServerException("Failed to get data"));
    }

    private <T> T logJson(T response) {
        try {
            log.debug(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            return response;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new InternalServerException(e.getMessage());
        }
    }
}
