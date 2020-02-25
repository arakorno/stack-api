package com.github.arakorno.stackapi.entity;

import com.github.arakorno.stackapi.model.QuestionModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Question> of(QuestionModel questionModel) {
        return questionModel.getQuestionItems().stream()
                .map(item -> Question.builder().id(item.getId()).tags(item.getTags()).answerCount(item.getAnswerCount())
                        .creationDate(item.getCreationDate()).isAnswered(item.getIsAnswered())
                        .userId(item.getOwner().getUserId()).viewCount(item.getViewCount()).build())
                .collect(Collectors.toList());
    }
}
