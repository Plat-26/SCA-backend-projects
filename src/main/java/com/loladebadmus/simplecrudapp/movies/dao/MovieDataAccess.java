package com.loladebadmus.simplecrudapp.movies.dao;

import com.loladebadmus.simplecrudapp.movies.model.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("movie_list")
class MovieDataAccess implements MovieDao {

    final static List<Movie> movie_db = new ArrayList<>();

    @Override
    public int createMovie(UUID id, Movie movie) {
        movie_db.add(new Movie(id, movie.getTitle(), movie.getProducer(), movie.getDesc(), movie.isAvailable()));
        return 0;
    }

    @Override
    public List<Movie> readAllMovies() {
        return movie_db;
    }

    @Override
    public Optional<Movie> readMovieById(UUID id) {
        return movie_db.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
    }

    @Override
    public int updateMovieById(UUID id, Movie newMovie) {
        return readMovieById(id)
                .map(movie -> {
                    int index = movie_db.indexOf(movie);
                    if(index >= 0) {
                        movie_db.set(index, new Movie(id, newMovie.getTitle(), newMovie.getProducer(), newMovie.getDesc(), newMovie.isAvailable()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    @Override
    public int deleteMovieById(UUID id) {
        Optional<Movie> movie = readMovieById(id);
        if(movie.isEmpty()) {
            return 0;
        }
        movie_db.remove(movie.get());
        return 1;
    }
}
