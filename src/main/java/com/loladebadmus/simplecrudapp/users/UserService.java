package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.errors.DuplicateDataException;
import com.loladebadmus.simplecrudapp.errors.ResourceNotFoundException;
import com.loladebadmus.simplecrudapp.movies.MovieRepository;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import com.loladebadmus.simplecrudapp.rentals.RentalRepository;
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
    private final RentalRepository rentalRepository;

    @Autowired
    public UserService(UserRepository userRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
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
    }

    public void deleteUser(UUID id) {
        boolean exists = userRepository.existsById(id);
        if(!exists) {
            throw new ResourceNotFoundException(id);
        }
        Rental rental = rentalRepository.findByUserId(id).get();
        rental.setUser(null);
        User user = userRepository.findById(id).get();
        user.setRentals(null);
        userRepository.deleteById(id);
    }
}
