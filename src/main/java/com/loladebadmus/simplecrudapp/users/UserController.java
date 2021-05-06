package com.loladebadmus.simplecrudapp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addUser(@Valid @NotNull @RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable("id") UUID id,
                           @Valid @NotNull @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }
}
