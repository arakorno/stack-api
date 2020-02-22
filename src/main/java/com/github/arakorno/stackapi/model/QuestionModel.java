package com.github.arakorno.stackapi.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionModel {
    private long id;
    private List<String> tags;
}
