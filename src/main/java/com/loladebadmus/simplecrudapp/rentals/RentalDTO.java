package com.loladebadmus.simplecrudapp.rentals;

import java.util.UUID;

public class RentalDTO {
    String username;
    String movieTitle;

    public RentalDTO(String username, String movieTitle) {
        this.username = username;
        this.movieTitle = movieTitle;
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
