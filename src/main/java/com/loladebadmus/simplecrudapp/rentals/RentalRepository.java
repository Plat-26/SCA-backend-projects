package com.loladebadmus.simplecrudapp.rentals;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r WHERE r.movieTitle = ?1")
    Optional<Rental> getRentalByTitle(String Title);
}


