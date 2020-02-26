package com.github.arakorno.stackapi.entity;

import com.github.arakorno.stackapi.model.BaseModel;
import com.github.arakorno.stackapi.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements BaseModel {
    private Integer userId;
    private String displayName;
    private String creationDate;

    public static User of(UserModel.UserItem userItem) {
        return User.builder().userId(userItem.getUserId()).displayName(userItem.getDisplayName())
                .creationDate(userItem.getCreationDate()).build();
    }
}