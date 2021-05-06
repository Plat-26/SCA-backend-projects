package com.loladebadmus.simplecrudapp.rentals;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "Rental")
@Table(
        name = "rentals",
        uniqueConstraints = {
                @UniqueConstraint(name = "rental_unique_constraints", columnNames = "movie_title")
        }
)
public class Rental {

    @Id
    @SequenceGenerator(
            name = "rental_sequence",
            sequenceName = "rental_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rental_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @NotBlank
    @Column(
            name = "movie_title",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    private String movieTitle;

    @Column(
            name = "price",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    @Min(value = 10, message = "Price should not be less than 10")
    @Max(value = 500, message = "Price should not be greater then 500")
    @NotNull
    private Integer price;

    @NotNull
    @Column(
            name = "available",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean isAvailable;

    public Rental(@JsonProperty("id") Long id,
                  @JsonProperty("movie-title") String movieTitle,
                  @JsonProperty("price") int price,
                  @JsonProperty("available") boolean isAvailable) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Rental() {

    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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
