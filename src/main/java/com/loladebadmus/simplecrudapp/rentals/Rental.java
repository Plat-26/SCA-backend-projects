package com.loladebadmus.simplecrudapp.rentals;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Rental {
    private final UUID id;
    private final String movieTitle;
    private final int price;
    private final boolean isAvailable;

    public Rental(@JsonProperty("id") UUID id,
                  @JsonProperty("movie title") String movieTitle,
                  @JsonProperty("price") int price,
                  @JsonProperty("available") boolean isAvailable) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public UUID getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

}
