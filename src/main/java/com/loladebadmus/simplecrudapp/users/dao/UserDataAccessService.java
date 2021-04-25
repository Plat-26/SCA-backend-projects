package com.loladebadmus.simplecrudapp.users.dao;

import com.loladebadmus.simplecrudapp.users.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("userList")
public class UserDataAccessService implements UserDao {

    final static List<User> user_db = new ArrayList<>();

    @Override
    public int insertUser(UUID id, User user) {
        user_db.add(new User(id, user.getName()));
        return 0;
    }

    @Override
    public List<User> selectAllUsers() {
        return user_db;
    }

    @Override
    public Optional<User> selectUserById(UUID id) {
        return user_db.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public int updateUserById(UUID id, User updatedUser) {
        return selectUserById(id)
                .map(user -> {
                    int indexOfUser = user_db.indexOf(user);
                    if(indexOfUser >= 0) {
                        user_db.set(indexOfUser, new User(id, updatedUser.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    @Override
    public int deleteUserById(UUID id) {
        Optional<User> someUser = selectUserById(id);
        if(someUser.isEmpty()) {
            return 0;
        }
        user_db.remove(someUser.get());
        return 1;
    }
}
