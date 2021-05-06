package com.loladebadmus.simplecrudapp.rentals;

import com.loladebadmus.simplecrudapp.rentals.RentalDao;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {
    private final RentalDao rentalDao;

    @Autowired
    public RentalService(@Qualifier("rental_list") RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    public int addRental(Rental rental) {
        return rentalDao.createRental(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalDao.readAllRentals();
    }

    public Optional<Rental> getRentalById(UUID id) {
        return rentalDao.readRentalById(id);
    }

    public int updateRental(UUID id, Rental rental) {
        return rentalDao.updateRentalById(id, rental);
    }

    public int deleteRental(UUID id) {
        return rentalDao.deleteRentalById(id);
    }
}
