package com.loladebadmus.simplecrudapp.rentals;

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

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rental", id)
        );
    }

    @Transactional
    public void addRental(RentalDTO rentalDTO) {
        Rental rental = convertRentalDTOToEntity(rentalDTO);
        rental.setRentalTime(LocalDateTime.now());
        User user = rental.getUser();
        user.addRental(rental);
//        userService.updateUser(user.getId(), user);
        rentalRepository.save(rental);
    }


    @Transactional
    private Rental convertRentalDTOToEntity(RentalDTO rentalDTO) {
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
        return rental;
    }


    @Transactional
    public void updateRental(Long id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rental", id)
        );
        if(!Objects.equals(rental.getUser().getName(), rentalDTO.getUsername())) {
            throw new IllegalStateException("Only the initial user can update a rental, create a new rental instead");
        }

        rental.setMovie(movieService.getMovieByTitle(rentalDTO.getMovieTitle()));
        rental.setRentalTime(LocalDateTime.now());
    }


    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Rental", id)
        );
        User rentalUser = rental.getUser();
        rental.setUser(null);
        rentalUser.removeRental(rental);
        rentalRepository.deleteById(id);
    }

}
