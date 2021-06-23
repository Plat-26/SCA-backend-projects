package com.loladebadmus.simplecrudapp.users.registration;

import com.loladebadmus.simplecrudapp.errors.FailedRegistrationException;
import com.loladebadmus.simplecrudapp.users.User;
import com.loladebadmus.simplecrudapp.users.UserRole;
import com.loladebadmus.simplecrudapp.users.UserService;
import com.loladebadmus.simplecrudapp.users.email.EmailSender;
import com.loladebadmus.simplecrudapp.users.token.ConfirmationToken;
import com.loladebadmus.simplecrudapp.users.token.ConfirmationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final EmailValidator emailValidator;

    public RegistrationService(UserService userService, ConfirmationTokenService confirmationTokenService, EmailSender emailSender, EmailValidator emailValidator) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
        this.emailValidator = emailValidator;
    }

    public String register(RegistrationRequestDTO requestDTO) {
        boolean isValidEmail = emailValidator.test(requestDTO.getEmail());

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
        String link = "http://localhost:8080/users/register/confirm?token=" + token;
        emailSender.send(requestDTO.getEmail(), buildEmail(requestDTO.getFirstName(), link));
        return token;
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if(confirmationToken.getConfirmedAt() != null) {
            throw new FailedRegistrationException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new FailedRegistrationException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String firstName, String link) {
        //todo: build html email
        return "";
    }

}
