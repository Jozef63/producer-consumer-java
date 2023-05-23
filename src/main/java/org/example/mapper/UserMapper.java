package org.example.mapper;

import org.example.db.model.UserEntity;
import org.example.domain.model.User;

public class UserMapper {

    public static User fromEntity(UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getGuid(), userEntity.getId());
    }
}
