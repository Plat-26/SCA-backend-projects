package com.loladebadmus.simplecrudapp.rentals;

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

    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
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

    //
//    @Id
//    @SequenceGenerator(
//            name = "rental_sequence",
//            sequenceName = "rental_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "rental_sequence"
//    )
//    @Column(
//            name = "id",
//            updatable = false
//    )
//    private Long id;
//
//    @NotBlank
//    @Column(
//            name = "movie_title",
//            nullable = false,
//            columnDefinition = "VARCHAR(25)"
//    )
//    private String movieTitle;
//
//    @Column(
//            name = "price",
//            nullable = false,
//            columnDefinition = "INTEGER"
//    )
//    @Min(value = 10, message = "Price should not be less than 10")
//    @Max(value = 500, message = "Price should not be greater then 500")
//    @Pattern(regexp = "\\d")
//    @NotNull
//    private Integer price;
//
//    @NotNull
//    @Column(
//            name = "available",
//            nullable = false,
//            columnDefinition = "BOOLEAN"
//    )
//    private boolean isAvailable;
//
//    public Rental(@JsonProperty("id") Long id,
//                  @JsonProperty("movie-title") String movieTitle,
//                  @JsonProperty("price") int price,
//                  @JsonProperty("available") boolean isAvailable) {
//        this.id = id;
//        this.movieTitle = movieTitle;
//        this.price = price;
//        this.isAvailable = isAvailable;
//    }
//
//    public Rental() {
//
//    }
//
//    public void setMovieTitle(String movieTitle) {
//        this.movieTitle = movieTitle;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public void setAvailable(boolean available) {
//        isAvailable = available;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getMovieTitle() {
//        return movieTitle;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public boolean isAvailable() {
//        return isAvailable;
//    }
}
