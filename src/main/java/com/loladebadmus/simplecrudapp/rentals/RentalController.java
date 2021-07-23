package com.loladebadmus.simplecrudapp.rentals;

import com.loladebadmus.simplecrudapp.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
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
    public RentalDTO createRentalFromDTO(@Valid @NotNull @RequestBody RentalDTO rentalDTO) {
        Rental createdRental = rentalService.addRental(rentalDTO);
        return convertRentalEntityToDTO(createdRental);
    }

    @GetMapping
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentalList = rentalService.getAllRentals();
        return convertRentalListToListOfDTOs(rentalList);
    }


    @GetMapping(path = "{rentalId}")
    public RentalDTO getRentalById(@Valid @NotBlank @PathVariable Long rentalId, Principal principal) throws IllegalAccessException {

        Rental loadedRental = rentalService.getRentalById(rentalId);

        if(!principal.getName().equals(loadedRental.getUser().getEmail())) {
            throw new IllegalAccessException("This resource can only be accessed by the owner");
        }
        return convertRentalEntityToDTO(loadedRental);
    }

    @PutMapping(path = "{rentalId}")
    public void updateRental(@PathVariable Long rentalId, @Valid @NotNull @RequestBody RentalDTO rentalDTO) throws IllegalAccessException {
        rentalService.updateRental(rentalId, rentalDTO);
    }

    @GetMapping("/user/{userId}")
    public RentalDTO getRentalByUserId(@PathVariable UUID userId, Principal principal) throws IllegalAccessException {
        if(!principal.getName().equals(userService.getUserById(userId).getEmail())) {
            throw new IllegalAccessException("This resource can only be accessed by the owner");
        }
        return convertRentalEntityToDTO(rentalService.getRentalByUserId(userId));
    }

    @DeleteMapping(path = "{id}")
    public void deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
    }


    @Transactional
    RentalDTO convertRentalEntityToDTO(Rental createdRental) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setRentalId(createdRental.getId());
        rentalDTO.setMovieTitle(createdRental.getMovie().getTitle());
        rentalDTO.setUsername(createdRental.getUser().getUsername());
        rentalDTO.setCreatedAt(createdRental.getRentalTime());
        rentalDTO.setRentalCost(createdRental.getMovie().getPrice());
        return rentalDTO;
    }

    @Transactional
    List<RentalDTO> convertRentalListToListOfDTOs(List<Rental> rentalList) {
        if(rentalList.isEmpty()) {
            return new ArrayList<>();
        }
        List<RentalDTO> dtoList = new ArrayList<>();
        for(Rental rental : rentalList) {
            dtoList.add(convertRentalEntityToDTO(rental));
        }
        return dtoList;
    }

}
