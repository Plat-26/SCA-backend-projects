package com.loladebadmus.simplecrudapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM users WHERE u.name = ?1")
    Optional<User> getUserByName(String name);
}
