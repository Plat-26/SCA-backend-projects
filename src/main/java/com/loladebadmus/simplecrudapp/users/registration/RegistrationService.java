package com.loladebadmus.simplecrudapp.users.registration;

import com.loladebadmus.simplecrudapp.errors.FailedRegistrationException;
import com.loladebadmus.simplecrudapp.users.User;
import com.loladebadmus.simplecrudapp.users.UserRole;
import com.loladebadmus.simplecrudapp.users.UserService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;

    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public String register(RegistrationRequestDTO requestDTO) {
        //todo validate user email string
        boolean isValidEmail = true;

        if(!isValidEmail) {
            throw new FailedRegistrationException("The provided email is invalid");
        }
        String token = userService.signUpUser(new User(
                requestDTO.getFirstName(),
                requestDTO.getLastName(),
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                UserRole.USER
        ));
        //todo: send verification email
        return token;
    }

}
