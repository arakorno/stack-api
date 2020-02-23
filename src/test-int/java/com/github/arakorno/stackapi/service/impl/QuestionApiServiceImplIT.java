package com.github.arakorno.stackapi.service.impl;

import com.github.arakorno.stackapi.AbstractIntTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class QuestionApiServiceImplIT extends AbstractIntTest {
    @Autowired
    private QuestionApiServiceImpl service;
    @Mock
    private RestTemplate restTemplate;
    @Value("classpath:json/api_response_questions.json")
    private Resource expectedResponse;
    protected static final String QUESTIONS_PATH = "/questions";

    @Test
    public void testGetQuestions() throws Exception {
        String mockResponse = OBJECT_MAPPER
                .writeValueAsString(OBJECT_MAPPER.readTree(Files.readAllBytes(expectedResponse.getFile().toPath())));

        API_STACK_SERVER.when(request().withPath("/questions/featured").withMethod(HttpMethod.GET.name()))
                .respond(response().withHeaders(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                        .withBody(mockResponse).withStatusCode(HttpStatus.OK.value()));

        mockMvc.perform(get(QUESTIONS_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(4))
                .andExpect(jsonPath("$.items[0].tags[1]").value("mysql"))
                .andExpect(jsonPath("$.items[0].is_answered").value(true))
                .andExpect(jsonPath("$.items[0].answer_count").value(2));

    }
}
