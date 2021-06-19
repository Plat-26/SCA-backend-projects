package com.loladebadmus.simplecrudapp.rentals;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class RentalDTO {

    @NotBlank(message = "Please enter an existing username to create a rental")
    private String username;
    @NotBlank(message = "Please enter a movie title to create a rental")
    private String movieTitle;

    public RentalDTO(@JsonProperty("username") String username,
                     @JsonProperty("movie-title") String movieTitle) {
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
