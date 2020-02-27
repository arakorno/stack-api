package com.github.arakorno.stackapi.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EpochToDateSerializer extends JsonDeserializer<String> {

    public static final SimpleDateFormat ISO_8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public EpochToDateSerializer() {
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        long epoch = jsonParser.getNumberValue().longValue() * 1000;
        Date date = new Date(epoch);
        return ISO_8601_DATE_FORMAT.format(date);
    }
}
