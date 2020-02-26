package com.github.arakorno.stackapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StackApiApplication.class })
@ActiveProfiles("test-int")
@AutoConfigureMockMvc
public class AbstractIntTest {

    @Autowired
    protected MockMvc mockMvc;
    protected static final String QUESTIONS_PATH = "/questions";
    protected static ClientAndServer API_STACK_SERVER;

    @BeforeClass
    public static void beforeClass() throws Exception {
        File jsonData = new ClassPathResource("json/api_response_questions.json").getFile();
        API_STACK_SERVER = ClientAndServer.startClientAndServer(8081);
        String mockResponse = OBJECT_MAPPER.writeValueAsString(OBJECT_MAPPER.readTree(jsonData));
        API_STACK_SERVER.when(request().withPath("/questions/featured").withMethod(HttpMethod.GET.name()))
                .respond(response().withHeaders(new Header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                        .withBody(mockResponse).withStatusCode(HttpStatus.OK.value()));
    }

    @AfterClass
    public static void afterClass() {
        API_STACK_SERVER.stop();
    }

    @Before
    public void before() {
        API_STACK_SERVER.reset();
    }

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY).enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
            .enable(SerializationFeature.INDENT_OUTPUT);
}
