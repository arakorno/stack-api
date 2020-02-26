package com.github.arakorno.stackapi.utill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arakorno.stackapi.exception.StackexchangeApiException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonLogUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T logJson(T response) {
        try {
            log.debug(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            return response;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new StackexchangeApiException(e.getMessage());
        }
    }
}
