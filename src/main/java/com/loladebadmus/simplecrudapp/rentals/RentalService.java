package com.loladebadmus.simplecrudapp.rentals;

import com.loladebadmus.simplecrudapp.errors.DuplicateDataException;
import com.loladebadmus.simplecrudapp.errors.IDNotFoundException;
import com.loladebadmus.simplecrudapp.errors.ResourceNotFoundException;
import com.loladebadmus.simplecrudapp.movies.Movie;
import com.loladebadmus.simplecrudapp.movies.MovieService;
import com.loladebadmus.simplecrudapp.users.User;
import com.loladebadmus.simplecrudapp.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MovieService movieService;
    private final UserService userService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, MovieService movieService, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.movieService = movieService;
        this.userService = userService;
    }

//    public void addRental(Rental rental) {
//        Optional<Rental> rentalOptional = rentalRepository
//                .getRentalByTitle(rental.getMovieTitle());
//        if(rentalOptional.isPresent()) {
//            throw new DuplicateDataException("movie-title", rental.getMovieTitle());
//        }
//        rentalRepository.save(rental);
//    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(
                () -> new IDNotFoundException("Rental", id)
        );
    }


    @Transactional
    public Rental convertRentalDTOToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        Movie movie = movieService.getMovieByTitle(rentalDTO.getMovieTitle());
        if(movie == null) {
            throw new ResourceNotFoundException("Movie not found, try another title!");
        }
        User user = userService.getUserByName(rentalDTO.getUsername());
        if(user == null) {
            throw new ResourceNotFoundException("User not found. Please check username or register ");
        }
        rental.setUser(user);
        rental.setMovie(movie);
        rental.setRentalTime(LocalDateTime.now());
        user.addRental(rental);
        userService.updateUser(user.getId(), user);
        rentalRepository.save(rental);
        return rental;
    }


//    @Transactional
//    public void updateRental(Long id, RentalDTO rentalDTO) {
//        Rental rental = rentalRepository.findById(id).orElseThrow(
//                () -> new IDNotFoundException("Rental", id)
//        );
//
//        this.updateRental(rental, rentalDTO);
//    }


//    @Transactional
//    public void updateRental(Long id, Rental rentalUpdate) {
//        Rental rental = rentalRepository.findById(id).orElseThrow(
//                () -> new IDNotFoundException("Rental", id)
//        );
//        if(!Objects.equals(rental.getMovieTitle(), rentalUpdate.getMovieTitle())) {
//            Optional<Rental> rentalOptional = rentalRepository
//                    .getRentalByTitle(rentalUpdate.getMovieTitle());
//            if(rentalOptional.isPresent()) {
//                throw new DuplicateDataException("movie-tile", rentalUpdate.getMovieTitle());
//            }
//        }
//
//        this.updateRental(rental, rentalUpdate);
//    }

    public void deleteRental(Long id) {
        boolean exists = rentalRepository.existsById(id);
        if(!exists) {
            throw new IDNotFoundException("Rental", id);
        }
    }

//    private void updateRental(Rental current, Rental rentalUpdate) {
//        current.setMovieTitle(rentalUpdate.getMovieTitle());
//        current.setPrice(rentalUpdate.getPrice());
//        current.setAvailable(rentalUpdate.isAvailable());
//    }
}
