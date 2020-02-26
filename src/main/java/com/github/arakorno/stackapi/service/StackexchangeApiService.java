package com.github.arakorno.stackapi.service;

import com.github.arakorno.stackapi.model.QuestionModel;
import com.github.arakorno.stackapi.model.UserModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface StackexchangeApiService {
    String API_ERROR_MESSAGE = "Failed to get data";
    String QUESTION_URL = "/questions/featured";
    String USER_DETAILS_URL = "/users/{userId}";

    default HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    QuestionModel getQuestions();

    UserModel getUsers(Integer userId);
}
