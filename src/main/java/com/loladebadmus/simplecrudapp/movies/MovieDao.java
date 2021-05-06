package com.loladebadmus.simplecrudapp.movies;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieDao {

    int createMovie(UUID id, Movie movie);

    default int createMovie(Movie movie) {
        UUID id = UUID.randomUUID();
        return createMovie(id, movie);
    }

    List<Movie> readAllMovies();

    Optional<Movie> readMovieById(UUID id);

    int updateMovieById(UUID id, Movie newMovie);

    int deleteMovieById(UUID id);
}
