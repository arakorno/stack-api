package com.github.arakorno.stackapi.config;

import com.github.arakorno.stackapi.exception.InternalServerException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

@Configuration
@Log4j2
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "rest.connect")
    public ClientHttpRequestFactory httpRequestFactory() {
        return new SimpleClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode() != HttpStatus.OK;
            }

            public void handleError(ClientHttpResponse response) throws IOException {
                String error = format("itnvalid response received - status: %d", response.getStatusCode().value());
                try {
                    error += String.format(", message: %s",
                            IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
                } catch (Exception e) {
                    log.error("Failed to get response message", e);
                }
                throw new InternalServerException(error);
            }
        };

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }
}
