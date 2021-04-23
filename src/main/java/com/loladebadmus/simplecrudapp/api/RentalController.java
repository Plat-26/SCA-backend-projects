package com.loladebadmus.simplecrudapp.api;

import com.loladebadmus.simplecrudapp.model.Rental;
import com.loladebadmus.simplecrudapp.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/rental")
@RestController
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public void addRental(@RequestBody Rental rental) {
        rentalService.addRental(rental);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping(path = "{id}")
    public Optional<Rental> getRentalById(@PathVariable("id") UUID id) {
        return rentalService.getRentalById(id);
    }

    @PutMapping(path = "{id}")
    public void updateRental(@PathVariable UUID id, @RequestBody Rental rental) {
        rentalService.updateRental(id, rental);
    }

    @DeleteMapping(path = "{id}")
    public void deleteRental(@PathVariable UUID id) {
        rentalService.deleteRental(id);
    }
}
