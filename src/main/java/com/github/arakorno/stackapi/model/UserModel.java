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
public class UserModel implements BaseModel {
    @JsonProperty("items")
    private List<UserItem> userItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserItem implements BaseModel {
        @JsonProperty("user_id")
        private Integer userId;
        @JsonProperty("display_name")
        private String displayName;
        @JsonProperty("creation_date")
        @JsonDeserialize(using = EpochToDateSerializer.class)
        private String creationDate;
    }
}
