package com.github.arakorno.stackapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.arakorno.stackapi.converter.EpochToDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionModel {
    private List<Item> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Item {
        @JsonProperty("question_id")
        private Integer id;
        @JsonProperty
        private List<String> tags;
        @JsonProperty("is_answered")
        private Boolean isAnswered;
        @JsonProperty("creation_date")
        @JsonDeserialize(using = EpochToDateSerializer.class)
        private String creationDate;
        @JsonProperty("view_count")
        private Integer viewCount;
        @JsonProperty("answer_count")
        private Integer answerCount;
        @JsonProperty
        private Owner owner;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Owner {
        @JsonProperty("user_id")
        private Integer userId;
    }
}
