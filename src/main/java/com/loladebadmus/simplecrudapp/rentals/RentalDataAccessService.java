package com.loladebadmus.simplecrudapp.rentals;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("rental_list")
public class RentalDataAccessService implements RentalDao {

    final static List<Rental> rental_db = new ArrayList<>();

    @Override
    public int createRental(UUID id, Rental rental) {
        rental_db.add(new Rental(id, rental.getMovieTitle(), rental.getPrice(), rental.isAvailable()));
        return 0;
    }

    @Override
    public List<Rental> readAllRentals() {
        return rental_db;
    }

    @Override
    public Optional<Rental> readRentalById(UUID id) {
        return rental_db.stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst();
    }

    @Override
    public int updateRentalById(UUID id, Rental newRental) {
        return readRentalById(id)
                .map(rental -> {
                    int index = rental_db.indexOf(rental);
                    if(index >= 0) {
                        rental_db.set(index, new Rental(id, newRental.getMovieTitle(), newRental.getPrice(), newRental.isAvailable()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    @Override
    public int deleteRentalById(UUID id) {
        Optional<Rental> rental = readRentalById(id);
        if(rental.isEmpty()) {
            return 0;
        }
        rental_db.remove(rental.get());
        return 1;
    }
}
