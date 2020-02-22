package com.github.arakorno.stackapi.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "question")
public class Question {
    @Id
    private Integer id;
    private List<String> tags;
    private Boolean isAnswered;
    private String creationDate;
    private Integer viewCount;
    private Integer answerCount;
    private Integer userId;
}
