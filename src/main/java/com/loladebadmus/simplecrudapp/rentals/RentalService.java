package com.loladebadmus.simplecrudapp.rentals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void addRental(Rental rental) {
        Optional<Rental> rentalOptional = rentalRepository
                .getRentalByTitle(rental.getMovieTitle());
        if(rentalOptional.isPresent()) {
            throw new IllegalStateException("Rental with movie title " + rental.getMovieTitle() + " already exists");
        }
        rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Rental with id " + id + " not in database")
        );
    }

    @Transactional
    public void updateRental(Long id, Rental rentalUpdate) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Rental with id " + id + "not in database")
        );
        if(!Objects.equals(rental.getMovieTitle(), rentalUpdate.getMovieTitle())) {
            Optional<Rental> rentalOptional = rentalRepository
                    .getRentalByTitle(rentalUpdate.getMovieTitle());
            if(rentalOptional.isPresent()) {
                throw new IllegalStateException("Rental with movie title " + rental.getMovieTitle() + " already exists");
            }
        }

        this.updateRental(rental, rentalUpdate);
    }

    public void deleteRental(Long id) {
        boolean exists = rentalRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("Rental with id " + id + " not in database");
        }
    }

    private void updateRental(Rental current, Rental rentalUpdate) {
        current.setMovieTitle(rentalUpdate.getMovieTitle());
        current.setPrice(rentalUpdate.getPrice());
        current.setAvailable(rentalUpdate.isAvailable());
    }
}
