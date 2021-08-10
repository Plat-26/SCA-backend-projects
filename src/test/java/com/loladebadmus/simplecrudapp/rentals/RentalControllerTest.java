package com.loladebadmus.simplecrudapp.rentals;

import com.loladebadmus.simplecrudapp.movies.Movie;
import com.loladebadmus.simplecrudapp.users.User;
import com.loladebadmus.simplecrudapp.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class RentalControllerTest {

    @Mock
    RentalService rentalService;
    @Mock
    UserService userService;

    private Rental rental;
    private Movie movie;
    private User user;
    LocalDateTime time;

    @InjectMocks
    RentalController rentalController = new RentalController(rentalService, userService);

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setTitle("How to get away with murder");
        movie.setPrice(500.0);

        user = new User();
        user.setId(UUID.fromString("d27d6f5f-e000-4320-b121-7bac28241c80"));
        user.setEmail("johndoe@email.com");

        rental = new Rental();
        rental.setMovie(movie);
        time = LocalDateTime.now();
        rental.setRentalTime(time);
        rental.setUser(user);
    }

    @Test
    void convertRentalEntityToDTO_happy_path() {

        RentalDTO expectedDTO = rentalController.convertRentalEntityToDTO(rental);

        assertThat(expectedDTO).isNotNull();
        assertThat(expectedDTO.getMovieTitle()).isEqualTo(movie.getTitle());
        assertThat(expectedDTO.getUsername()).isEqualTo(user.getEmail());
        assertThat(expectedDTO.getCreatedAt()).isEqualTo(time);
    }


    @Test
    void convertRentalListToListOfDTOs_happy_path() {
        //given a list of rentals
        List<Rental> rentals = Arrays.asList(rental);
        //then
        List<RentalDTO> listOfDTOs = rentalController.convertRentalListToListOfDTOs(rentals);
        RentalDTO expectedDTO = listOfDTOs.get(0);

        //tests
        assertThat(listOfDTOs).isNotNull();
        assertThat(expectedDTO).isNotNull();
        assertThat(expectedDTO.getMovieTitle()).isEqualTo(movie.getTitle());
        assertThat(expectedDTO.getUsername()).isEqualTo(user.getEmail());

        //given a list of rentals
        List<Rental> emptyList = Arrays.asList();
        List<RentalDTO> emptyDto = rentalController.convertRentalListToListOfDTOs(emptyList);
        assertThat(emptyDto).isEmpty();
    }
}