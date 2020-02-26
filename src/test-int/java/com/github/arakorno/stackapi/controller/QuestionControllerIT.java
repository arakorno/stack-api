package com.github.arakorno.stackapi.controller;

import com.github.arakorno.stackapi.AbstractIntTest;
import com.github.arakorno.stackapi.service.QuestionService;
import org.junit.Test;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionControllerIT extends AbstractIntTest {
    @Value("classpath:json/api_response_questions.json")
    private Resource expectedResponse;

    @Autowired
    private QuestionService questionService;

    @Test
    public void testGetQuestions() throws Exception {
        mockMvc.perform(get(QUESTIONS_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4)).andExpect(jsonPath("$[0].tags[1]").value("mysql"))
                .andExpect(jsonPath("$[0].isAnswered").value(true)).andExpect(jsonPath("$[0].answerCount").value(2));

    }

    @Test
    public void testGetQuestion() throws Exception {
        final int QUESTION_ID = 60333049;
        mockMvc.perform(get(QUESTIONS_PATH + "/" + QUESTION_ID).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tags[2]").value("regex")).andExpect(jsonPath("$.isAnswered").value(false))
                .andExpect(jsonPath("$.answerCount").value(0));

    }

    @Test
    public void testGetQuestionsByTags() throws Exception {
        String[] tags = List.of("c#", "mysql").toArray(String[]::new);
        mockMvc.perform(get(QUESTIONS_PATH + "/by").param("tags", tags).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].tags[0]").value("c#")).andExpect(jsonPath("$[0].tags[1]").value("mysql"))
                .andExpect(jsonPath("$[1].tags[0]").value("c#")).andExpect(jsonPath("$[1].tags[2]").value("mysql"))
                .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        final int QUESTION_ID = 60333049;
        mockMvc.perform(delete(QUESTIONS_PATH + "/" + QUESTION_ID).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        mockMvc.perform(get(QUESTIONS_PATH + "/" + QUESTION_ID).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
        String mockResponse = OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readTree(expectedResponse.getFile()));
        API_STACK_SERVER.when(request().withPath("/questions/featured").withMethod(HttpMethod.GET.name()))
                .respond(response().withHeaders(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                        .withBody(mockResponse).withStatusCode(HttpStatus.OK.value()));
        questionService.populateDataSource();
    }
}