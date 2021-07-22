package com.loladebadmus.simplecrudapp.movies;

import com.loladebadmus.simplecrudapp.errors.DuplicateDataException;
import com.loladebadmus.simplecrudapp.errors.ResourceNotFoundException;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import com.loladebadmus.simplecrudapp.rentals.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RentalRepository rentalRepository;
//    private final RentalService rentalService;

    @Autowired
    public MovieService(MovieRepository movieRepository, RentalRepository rentalRepository) {
        this.movieRepository = movieRepository;
        this.rentalRepository = rentalRepository;
    }

    public void addMovie(Movie movie) {
        Optional<Movie> movieOptional = movieRepository
                .getMovieByTitle(movie.getTitle());
        if(movieOptional.isPresent()) {
            throw new DuplicateDataException("title", movie.getTitle());
        }
        movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Movie" , id)
        );
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.getMovieByTitle(title).orElseThrow(
                () -> new ResourceNotFoundException("Movie title not mapped, try another title")
        );
    }

    @Transactional
    public void updateMovie(Long id, Movie movieUpdate) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Movie" , id)
        );
        if(!Objects.equals(movie.getTitle(), movieUpdate.getTitle())) {
            Optional<Movie> movieOptional = movieRepository
                    .getMovieByTitle(movieUpdate.getTitle());
            if(movieOptional.isPresent()) {
                throw new DuplicateDataException("title", movieUpdate.getTitle());
            }
        }
        this.updateMovie(movie, movieUpdate);
    }

    public void deleteMovie(Long id) {
        boolean exists = movieRepository.existsById(id);
        if(!exists) {
            throw new ResourceNotFoundException("Movie" , id);

        }
        Rental rental = rentalRepository.findByMovieId(id).orElseThrow(
                () -> new ResourceNotFoundException("")
        );
        rental.setMovie(null);
        movieRepository.deleteById(id);
    }

    private void updateMovie(Movie current, Movie movieUpdate) {
        current.setTitle(movieUpdate.getTitle());
        current.setProducer(movieUpdate.getProducer());
        current.setAvailable(movieUpdate.isAvailable());
        current.setDesc(movieUpdate.getDesc());
    }
}
