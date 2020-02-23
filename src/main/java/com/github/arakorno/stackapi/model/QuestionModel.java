package com.github.arakorno.stackapi.model;

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
public class QuestionModel implements BaseModel {
    private List<Item> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item implements BaseModel {
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
    public static class Owner implements BaseModel {
        @JsonProperty("user_id")
        private Integer userId;
    }
}
