package com.loladebadmus.simplecrudapp.registration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetPasswordDTO {
    @NotBlank(message = "Please enter a password")
    @Size(min = 7, message = "Password must be greater than 7 characters")
    private String password;
    @NotNull(message = "Passwords do not match")
    private String confirmPassword;

    public ResetPasswordDTO(@NotBlank(message = "Please enter a password") @Size(min = 7, message = "Password must be greater than 7 characters") String password, @NotNull(message = "Passwords do not match") String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
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
