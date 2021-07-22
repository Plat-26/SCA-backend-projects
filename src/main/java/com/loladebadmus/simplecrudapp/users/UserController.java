package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.registration.RegistrationRequestDTO;
import com.loladebadmus.simplecrudapp.registration.RegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RegistrationService registrationService;

    @Autowired
    public UserController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public String addUser(@Valid @NotNull @RequestBody RegistrationRequestDTO requestDTO) {
        return registrationService.register(requestDTO);
    }

    @GetMapping("/register/confirm")
    public String confirmUserToken(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return convertListOfUsersToDTOs((ArrayList<User>) userService.getAllUsers());
    }

    @GetMapping(path = "{id}")
    public UserDTO getUserById(@PathVariable("id") @NotBlank UUID id, Principal principal) throws IllegalAccessException {

        User loadedUser = userService.getUserById(id);
        if(!principal.getName().equals(loadedUser.getEmail())) {
            throw new IllegalAccessException("This resource can only be accessed by owner");
        }
        return convertUserToUserDTO(loadedUser);
    }

    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable("id") @NotBlank UUID id,
                           @Valid @NotNull @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }

    private UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setRentalList(user.getRentals());
        return userDTO;
    }

    private ArrayList<UserDTO> convertListOfUsersToDTOs(ArrayList<User> users) {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        for(User user: users) {
            userDTOS.add(convertUserToUserDTO(user));
        }
        return userDTOS;
    }
}
