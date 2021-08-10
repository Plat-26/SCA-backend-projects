package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.registration.RegistrationService;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {

    @Mock
    UserService userService;
    @Mock
    RegistrationService registrationService;

    @InjectMocks
    UserController userController = new UserController(userService, registrationService);

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("susiestone@email.com");
        user.setId(UUID.fromString("d27d6f5f-e000-4320-b121-7bac28241c80"));
        user.setFirstName("Susie");
        user.setLastName("Stone");
    }

    @Test
    void convertUserToUserDTO_happy_path() {
        UserDTO expectedDTO = userController.convertUserToUserDTO(user);

        assertThat(expectedDTO).isNotNull();
        assertThat(expectedDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(expectedDTO.getUsername()).isEqualTo(user.getEmail());
        assertThat(expectedDTO.getLastName()).isEqualTo(user.getLastName());
    }

    @Test
    void convertListOfUsersToDTOs_happy_path() {

        Rental rental1 = new Rental();
        rental1.setId(10L);
        Rental rental2 = new Rental();
        rental1.setId(10L);

        user.setRentals(Arrays.asList(rental1, rental2));

        List<User> users = Arrays.asList(user);
        ArrayList<UserDTO> listOfDTOs = userController.convertListOfUsersToDTOs(users);
        assertThat(listOfDTOs).isNotNull();
        UserDTO expectedDTO = listOfDTOs.get(0);

        assertThat(expectedDTO).isNotNull();
        assertThat(expectedDTO.getRentalIds()).contains(rental1.getId(), rental2.getId());
        assertThat(expectedDTO.getUsername()).isEqualTo(user.getEmail());


    }
}