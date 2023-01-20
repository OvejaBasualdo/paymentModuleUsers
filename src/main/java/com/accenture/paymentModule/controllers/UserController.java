package com.accenture.paymentModule.controllers;

import com.accenture.paymentModule.models.User;
import com.accenture.paymentModule.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
