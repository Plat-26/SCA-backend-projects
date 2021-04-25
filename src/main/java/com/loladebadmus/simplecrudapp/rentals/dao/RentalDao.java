package com.loladebadmus.simplecrudapp.rentals.dao;

import com.loladebadmus.simplecrudapp.rentals.model.Rental;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalDao {

    int createRental(UUID id, Rental Rental);

    default int createRental(Rental rental) {
        UUID id = UUID.randomUUID();
        return createRental(id, rental);
    }

    List<Rental> readAllRentals();

    Optional<Rental> readRentalById(UUID id);

    int updateRentalById(UUID id, Rental newRental);

    int deleteRentalById(UUID id);
}
