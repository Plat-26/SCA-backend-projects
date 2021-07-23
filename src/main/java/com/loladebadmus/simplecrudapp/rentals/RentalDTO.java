package com.loladebadmus.simplecrudapp.rentals;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class RentalDTO {

    private Long rentalId;
    private Long userId;
    @NotBlank(message = "Please enter a registered email/username to create a rental")
    private String username;
    @NotBlank(message = "Please enter a movie title to create a rental")
    private String movieTitle;
    private Double rentalCost;
    private LocalDateTime createdAt;



    public RentalDTO(Long rentalId, Long userId, @JsonProperty("username") String username,
                     @JsonProperty("movie-title") String movieTitle, Double rentalCost, LocalDateTime createdAt) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.username = username;
        this.movieTitle = movieTitle;
        this.rentalCost = rentalCost;
        this.createdAt = createdAt;
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

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(Double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
