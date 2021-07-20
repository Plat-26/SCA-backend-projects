package com.loladebadmus.simplecrudapp.rentals;

import com.loladebadmus.simplecrudapp.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;


    @Autowired
    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }


    @PostMapping
    public void createRentalFromDTO(@Valid @NotNull @RequestBody RentalDTO rentalDTO) {
        rentalService.addRental(rentalDTO);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping(path = "{id}")
    public Rental getRentalById(@Valid @NotBlank @PathVariable("id") Long id, Principal principal) throws IllegalAccessException {

        Rental loadedRental = rentalService.getRentalById(id);

        if(!principal.getName().equals(loadedRental.getUser().getEmail())) {
            throw new IllegalAccessException("This resource can only be accessed by the owner");
        }
        return loadedRental;
    }

    @PutMapping(path = "{rentalId}")
    public void updateRental(@PathVariable Long rentalId, @Valid @NotNull @RequestBody RentalDTO rentalDTO) throws IllegalAccessException {
        rentalService.updateRental(rentalId, rentalDTO);
    }

    @GetMapping("/user/{userId}")
    public Rental getRentalByUserId(@PathVariable UUID userId, Principal principal) {
        if(!principal.getName().equals(userService.getUserById(userId).getEmail())) {
            throw new AccessDeniedException("This resource can only be accessed by the owner");
        }
        return rentalService.getRentalByUserId(userId);
    }

    @DeleteMapping(path = "{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }
}
