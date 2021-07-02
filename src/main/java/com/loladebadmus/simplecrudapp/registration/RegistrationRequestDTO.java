package com.loladebadmus.simplecrudapp.registration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class RegistrationRequestDTO {
    @NotBlank(message = "Please enter your first name")
    private final String firstName;
    @NotBlank(message = "Please enter your last name")
    private final String lastName;
    @NotBlank(message = "Please enter your email address")
    private final String email;
    @NotBlank(message = "Please enter a password")
    private final String password;

    public RegistrationRequestDTO(
            @JsonProperty("first-name") String firstName,
            @JsonProperty("last-name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
