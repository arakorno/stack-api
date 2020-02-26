package com.github.arakorno.stackapi.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String swaggerBasePath;

    @Value("${SWAGGER_BASE_PATH}")
    public void setSwaggerBasePath(String swaggerBasePath) {
        this.swaggerBasePath = swaggerBasePath;
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
                .pathMapping(swaggerBasePath).apiInfo(apiInfo()).useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Service for interacting with stackexachange api",
                "This API provides functionality for retrieving data about questions and users.", "1.0", "TBC",
                new Contact("Andrew Pokidko", "http://www.github.com/arakorno", "andrew.pokidko@gmail.com"), "", "",
                new ArrayList<>());
    }
}
