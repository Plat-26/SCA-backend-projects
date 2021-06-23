package com.loladebadmus.simplecrudapp.users.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByUserId(long id);

    Optional<ConfirmationToken> findByToken(String token);
}
