package com.github.arakorno.stackapi.controller;

import com.github.arakorno.stackapi.AbstractIntTest;
import com.github.arakorno.stackapi.service.UserDetailsService;
import org.junit.Test;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIT extends AbstractIntTest {
    @Value("classpath:json/api_response_user_details.json")
    private Resource expectedResponse;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void testGetUser() throws Exception {
        final int userId = 12345;
        String mockResponse = OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readTree(expectedResponse.getFile()));
        HttpRequest userRequest = request().withPath("/users/" + userId).withMethod(HttpMethod.GET.name());
        API_STACK_SERVER.when(userRequest)
                .respond(response().withHeaders(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                        .withBody(mockResponse).withStatusCode(HttpStatus.OK.value()));
        mockMvc.perform(get(USERS_PATH + "/" + userId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.displayName").value("bunt"));

        mockMvc.perform(get(USERS_PATH + "/" + userId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.displayName").value("bunt"));

        // verify when we request user details repeatedly get it from cache
        API_STACK_SERVER.verify(userRequest, VerificationTimes.exactly(1));
    }
}