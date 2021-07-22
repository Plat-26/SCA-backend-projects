package com.loladebadmus.simplecrudapp.rentals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loladebadmus.simplecrudapp.movies.Movie;
import com.loladebadmus.simplecrudapp.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Rental")
@Table(name = "rentals")
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

    @Column(updatable = false)
    private LocalDateTime rentalTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rentals")
    private User user;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    public Rental(Long id, User user, Movie movie) {
        this.id = id;
        this.rentalTime = LocalDateTime.now();
        this.user = user;
        this.movie = movie;
    }

    public Rental() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(LocalDateTime rentalTime) {
        this.rentalTime = rentalTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
