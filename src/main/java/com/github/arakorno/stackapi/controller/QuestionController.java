package com.github.arakorno.stackapi.controller;

import com.github.arakorno.stackapi.model.QuestionModel;
import com.github.arakorno.stackapi.service.QuestionApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionApiService questionApiService;

    @GetMapping("/questions")
    public QuestionModel getQuestions() {
        return questionApiService.getQuestions();
    }
}
