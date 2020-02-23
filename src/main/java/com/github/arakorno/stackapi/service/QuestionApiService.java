package com.github.arakorno.stackapi.service;

import com.github.arakorno.stackapi.model.QuestionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface QuestionApiService {
    String QUESTION_URL = "/questions/featured";

    default HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    QuestionModel getQuestions();
}
