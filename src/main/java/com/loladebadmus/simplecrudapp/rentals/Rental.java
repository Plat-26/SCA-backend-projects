package com.loladebadmus.simplecrudapp.rentals;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(
        name = "rental",
        uniqueConstraints = {
                @UniqueConstraint(name = "rental_unique_constraints", columnNames = "movie-title")
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
            name = "movie-title",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    private String movieTitle;

    @NotBlank
    @Column(
            name = "price",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer price;

    @NotBlank
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
