package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.rentals.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDTO {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;
    private List<Long> rentalIds = new ArrayList<>();

    public UserDTO(UUID userId, String firstName, String lastName, String username) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public UserDTO() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Long> getRentalIds() {
        return rentalIds;
    }

    public void setRentalIds(List<Long> rentalIds) {
        this.rentalIds = rentalIds;
    }
}
