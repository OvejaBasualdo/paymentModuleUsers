package com.accenture.paymentModule.controllers;

import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.entity.ResultsType;
import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.models.Account;
import com.accenture.paymentModule.repository.UserRepository;
import com.accenture.paymentModule.service.IUserService;
import com.accenture.paymentModule.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    @Qualifier("userServiceRestTemplate")
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

    @GetMapping("/userAccountsId/{userId}")
    public List<Account> getUserByLastName(@PathVariable Long userId) {
        return userService.findAccountFromUserId(userId);
    }

    @PostMapping("/createUsers")
    public ResponseEntity<Object> createUsers(@RequestBody UserDTO user) {
        ResultsType result = userService.createUser(user);
        switch (result) {
            case SUCCESS:
                return new ResponseEntity<>("User created", HttpStatus.CREATED);
            case ACCOUNT_ADDED:
                return new ResponseEntity<>("Account added", HttpStatus.CREATED);
            case DNI_ERROR:
                return new ResponseEntity<>("Error in DNI field, please check it.", HttpStatus.NOT_ACCEPTABLE);
            case DNI_NUMBER_ERROR:
                return new ResponseEntity<>("DNI field must have 7 or 8 digits", HttpStatus.NOT_ACCEPTABLE);
            case DNI_EXISTENT:
                return new ResponseEntity<>("DNI is already registered", HttpStatus.NOT_ACCEPTABLE);
            case EMPTY_DATA:
                return new ResponseEntity<>("Missing data, please check all fields", HttpStatus.NOT_ACCEPTABLE);
            default:
                return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}
