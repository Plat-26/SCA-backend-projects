package com.loladebadmus.simplecrudapp.dao;

import com.loladebadmus.simplecrudapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    int insertUser(UUID id, User user);

    default int insertUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID id);

    int updateUserById(UUID id, User updatedUser);

    int deleteUserById(UUID id);
}
