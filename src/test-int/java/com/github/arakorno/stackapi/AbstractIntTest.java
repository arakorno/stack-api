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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StackApiApplication.class })
@ActiveProfiles("test-int")
@AutoConfigureMockMvc
public class AbstractIntTest {

    @Autowired
    protected MockMvc mockMvc;

    protected static ClientAndServer API_STACK_SERVER;

    @BeforeClass
    public static void beforeClass() {
        API_STACK_SERVER = ClientAndServer.startClientAndServer(8081);
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
