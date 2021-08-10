package com.loladebadmus.simplecrudapp.movies;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository ;

    @Test
    void getMovieByTitle_happy_path() {

        Movie movie = new Movie(
                "Cinderella",
                "Disney",
                "Fairytale",
                true,
                300.0
        );
        movieRepository.save(movie);

        Movie expectedMovie = movieRepository.getMovieByTitle("Cinderella").get();
        assertThat(expectedMovie).isNotNull();
        assertThat(expectedMovie.getDesc()).isEqualTo("Fairytale");
    }
}