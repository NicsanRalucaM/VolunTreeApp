package com.example.controller;

import com.example.entity.User;
import com.example.model.AllUsers;
import com.example.model.SingleUser;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public AllUsers getAllUsers() {
        AllUsers result = userService.getAllUsers();
        return result;
    }

    @GetMapping("{id}")
    public SingleUser getUserById(@PathVariable Integer id) {

        SingleUser result = userService.getUserById(id);
        return result;
    }

    @PostMapping
    public SingleUser createUser(@RequestBody User user) {
        SingleUser result = userService.createUser(user);
        return result;
    }

}
