package com.loladebadmus.simplecrudapp.registration.token;

import com.loladebadmus.simplecrudapp.errors.FailedRegistrationException;
import com.loladebadmus.simplecrudapp.users.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public String createConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(12),
                user
        );
        this.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(
                () -> new FailedRegistrationException("Token not find")
        );
    }

    public boolean setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(
                () -> new FailedRegistrationException("Token not found")
        );
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        return true;
    }
}
