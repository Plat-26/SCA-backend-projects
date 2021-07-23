package com.loladebadmus.simplecrudapp.registration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationRequestDTO {
    @NotBlank(message = "Please enter your first name")
    private String firstName;
    @NotBlank(message = "Please enter your last name")
    private String lastName;
    @NotBlank(message = "Please enter your email address")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "Please enter a password")
    @Size(min = 7, message = "Password must be greater than 7 characters")
    private String password;
    @NotNull(message = "Passwords do not match")
    private String confirmPassword;

    public RegistrationRequestDTO(
            @JsonProperty("first-name") String firstName,
            @JsonProperty("last-name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("confirm-password") String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        checkPassword();
    }

    private void checkPassword() {
        if(this.password == null || this.confirmPassword == null) {
            return;
        } else if (!this.password.equals(this.confirmPassword)){
            this.confirmPassword = null;
        }
    }
}
