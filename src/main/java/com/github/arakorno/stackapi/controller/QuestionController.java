package com.github.arakorno.stackapi.controller;

import com.github.arakorno.stackapi.entity.Question;
import com.github.arakorno.stackapi.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping()
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable("id") Integer id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/by")
    public List<Question> getQuestionsByTags(@RequestParam String[] tags) {
        return questionService.findQuestionsByTags(tags);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Integer id) {
        questionService.deleteQuestion(id);
    }
}
