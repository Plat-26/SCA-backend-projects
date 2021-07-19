package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.rentals.Rental;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private List<Rental> rentalList = new ArrayList<>();

    public UserDTO(String firstName, String lastName, String email, List<Rental> rentalList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rentalList = rentalList;
    }

    public UserDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rental> getRentalList() {
        return rentalList;
    }

    public void setRentalList(List<Rental> rentalList) {
        this.rentalList = rentalList;
    }
}
