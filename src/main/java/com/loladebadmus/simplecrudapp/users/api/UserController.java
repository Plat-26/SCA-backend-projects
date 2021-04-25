package com.loladebadmus.simplecrudapp.users.api;

import com.loladebadmus.simplecrudapp.users.model.User;
import com.loladebadmus.simplecrudapp.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id)
                .orElse( null);
    }

    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable("id") UUID id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }
}
