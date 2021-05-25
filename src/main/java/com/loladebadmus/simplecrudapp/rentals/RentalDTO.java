package com.loladebadmus.simplecrudapp.rentals;

import javax.validation.constraints.NotBlank;

public class RentalDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String movieTitle;

    public RentalDTO(String username, String movieTitle) {
        this.username = username;
        this.movieTitle = movieTitle;
    }

    public RentalDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
