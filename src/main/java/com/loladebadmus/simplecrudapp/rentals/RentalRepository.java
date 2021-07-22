package com.loladebadmus.simplecrudapp.rentals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> findByMovieId(Long id);
    Optional<Rental> findByUserId(UUID id);
}


