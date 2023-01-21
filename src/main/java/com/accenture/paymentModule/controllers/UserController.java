package com.accenture.paymentModule.controllers;

import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public List<User> userList() {
        return userService.findAll();
    }

    @GetMapping("/list/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/firstName/{firstName}")
    public User getUserByFirstName(@PathVariable String firstName) {
        return userService.findByFirstNameIgnoreCase(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    public User getUserByLastName(@PathVariable String lastName) {
        return userService.findByLastNameIgnoreCase(lastName);
    }

    /*@PostMapping("/createUser")
    public*/
}
