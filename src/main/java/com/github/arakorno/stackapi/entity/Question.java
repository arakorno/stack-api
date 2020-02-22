package com.github.arakorno.stackapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "question")
public class Question {
    @Id
    private Integer id;
    private List<String> tags;
    private Boolean isAnswered;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private String creationDate;
    private Integer viewCount;
    private Integer answerCount;
    private Integer userId;
}
