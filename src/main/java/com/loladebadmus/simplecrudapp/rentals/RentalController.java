package com.loladebadmus.simplecrudapp.rentals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

//    @PostMapping
//    public void addRental(@Valid @NotNull @RequestBody Rental rental) {
//        rentalService.addRental(rental);
//    }

    @PostMapping
    public void createRentalFromDTO(@RequestBody RentalDTO rentalDTO) {
        rentalService.convertRentalDTOToEntity(rentalDTO);
    }
//
//    @GetMapping("/rentals/{rentalId}/users/{userId}")
//    public List<Rental> getRentalByUserId(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "rentalId") Long rentalId) {
//
//    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping(path = "{id}")
    public Rental getRentalById(@Valid @NotBlank @PathVariable("id") Long id) {
        return rentalService.getRentalById(id);
    }

//    @PutMapping(path = "{id}")
//    public void updateRental(@PathVariable Long id, @Valid @NotNull@RequestBody Rental rental) {
//        rentalService.updateRental(id, rental);
//    }

    @DeleteMapping(path = "{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }
}
