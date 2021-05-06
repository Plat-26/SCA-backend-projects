package com.loladebadmus.simplecrudapp.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(Movie movie) {
        Optional<Movie> movieOptional = movieRepository
                .getMovieByTitle(movie.getTitle());
        if(movieOptional.isPresent()) {
            throw new IllegalStateException("Movie title is taken");
        }
        movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Movie with id " + id + "not in database")
        );
    }

    @Transactional
    public void updateMovie(Long id, Movie movieUpdate) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Movie with id " + id + "not in database")
        );
        if(!Objects.equals(movie.getTitle(), movieUpdate.getTitle())) {
            Optional<Movie> movieOptional = movieRepository
                    .getMovieByTitle(movie.getTitle());
            if(movieOptional.isPresent()) {
                throw new IllegalStateException("Movie title is taken");
            }
        }
        this.updateMovie(movie, movieUpdate);
    }

    public void deleteMovie(Long id) {
        boolean exists = movieRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException(
                    "Movie with id " + id + "not in database"
            );
        }
        movieRepository.deleteById(id);
    }

    private void updateMovie(Movie current, Movie movieUpdate) {
        current.setTitle(movieUpdate.getTitle());
        current.setProducer(movieUpdate.getProducer());
        current.setAvailable(movieUpdate.isAvailable());
        current.setDesc(movieUpdate.getDesc());
    }
}
