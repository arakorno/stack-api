package com.github.arakorno.stackapi.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arakorno.stackapi.model.UserModel;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class EpochToDateSerializerTest {
    @Test
    public void testDeserialize() throws IOException {
        String json = "{\n" + "  \"items\": [\n" + "    {\n" + "      \"creation_date\": 1334716152,\n"
                + "      \"user_id\": 12345\n" + "    }\n" + "  ]\n" + "}";
        UserModel userItem = new ObjectMapper().readValue(json, UserModel.class);
        assertTrue(userItem.getUserItems().get(0).getCreationDate().startsWith("2012-04-18T05:29:12"));
    }
}