package com.loladebadmus.simplecrudapp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        Optional<User> userOptional = userRepository.getUserByName(user.getName());
        if(userOptional.isPresent()) {
            throw new IllegalStateException("This name is already taken");
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("User with " + id + "not in database")
        );
    }

    @Transactional
    public void updateUser(UUID id, User newUser) {
        User user =  userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("User with" + id + "not in database")
        );
        if(!Objects.equals(user.getName(), newUser.getName())) {
            Optional<User> userOptional = userRepository.getUserByName(user.getName());
            if(userOptional.isPresent()) {
                throw new IllegalStateException("This name is already taken");
            }
        }
        user.setName(newUser.getName());
    }

    public void deleteUser(UUID id) {
        boolean exists = userRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("User with" + id + "not in database");
        }
        userRepository.deleteById(id);
    }
}
