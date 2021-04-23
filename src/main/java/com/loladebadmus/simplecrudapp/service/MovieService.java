package com.loladebadmus.simplecrudapp.service;

import com.loladebadmus.simplecrudapp.dao.MovieDao;
import com.loladebadmus.simplecrudapp.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieDao movieDao;

    @Autowired
    public MovieService(@Qualifier("movie_list") MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public int addMovie(Movie movie) {
        return movieDao.createMovie(movie);
    }

    public List<Movie> getAllMovies() {
        return movieDao.readAllMovies();
    }

    public Optional<Movie> getMovieById(UUID id) {
        return movieDao.readMovieById(id);
    }

    public int updateMovie(UUID id, Movie movie) {
        return movieDao.updateMovieById(id, movie);
    }

    public int deleteMovie(UUID id) {
        return movieDao.deleteMovieById(id);
    }
}
