package com.loladebadmus.simplecrudapp.rentals;

import com.loladebadmus.simplecrudapp.movies.Movie;
import com.loladebadmus.simplecrudapp.movies.MovieRepository;
import com.loladebadmus.simplecrudapp.users.User;
import com.loladebadmus.simplecrudapp.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByMovieId() {
        UUID userId = UUID.fromString("595fa1ab-8c77-41f0-9e9e-15c4f8954c95");

        User user = new User();
        user.setId(userId);
        userRepository.save(user);

        Movie movie = new Movie();
        movie.setId(1L);
        movieRepository.save(movie);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setMovie(movie);
        rentalRepository.save(rental);

        Rental expectedRental = rentalRepository.findByMovieId(1L).get();
        assertThat(expectedRental).isNotNull();
        assertThat(expectedRental.getMovie().getId()).isEqualTo(movie.getId());
    }

    @Test
    void findByUserId() {
    }
}