package com.github.arakorno.stackapi.controller;

import com.github.arakorno.stackapi.entity.Question;
import com.github.arakorno.stackapi.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@Api(tags = "Use these endpoints for getting questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping()
    @ApiOperation(value = "Find all questions")
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find question by id")
    public Question getQuestion(@PathVariable("id") Integer id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/by")
    @ApiOperation(value = "Find questions by tags")
    public List<Question> getQuestionsByTags(@RequestParam String[] tags) {
        return questionService.findQuestionsByTags(tags);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a question")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("id") Integer id) {
        questionService.deleteQuestion(id);
    }
}
