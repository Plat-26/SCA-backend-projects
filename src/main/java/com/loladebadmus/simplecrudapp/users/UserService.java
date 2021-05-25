package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.errors.DuplicateDataException;
import com.loladebadmus.simplecrudapp.errors.ResourceNotFoundException;
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
            throw new DuplicateDataException("username", user.getName());
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

    public User getUserByName(String username) {
        return userRepository.getUserByName(username).orElseThrow(
                () -> new ResourceNotFoundException("This user is missing, check name or register")
        );
    }

    @Transactional
    public void updateUser(UUID id, User newUser) {
        User user =  userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        if(!Objects.equals(user.getName(), newUser.getName())) {
            Optional<User> userOptional = userRepository.getUserByName(newUser.getName());
            if(userOptional.isPresent()) {
                throw new DuplicateDataException("name", newUser.getName());
            }
        }
        user.setName(newUser.getName());
//        ///todo:TRack new changes
//        user.updateRentals(newUser.retrieveRentals());
//        user.setRentals(newUser.getRentals());
    }

    public void deleteUser(UUID id) {
        boolean exists = userRepository.existsById(id);
        if(!exists) {
            throw new ResourceNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
