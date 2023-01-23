package com.accenture.paymentModule.controllers;

import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.repository.UserRepository;
import com.accenture.paymentModule.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

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
    public List<Long> getUserByLastName(@PathVariable Long userId) {
        return userService.findAccountFromUserId(userId);
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestParam String firstName, @RequestParam String lastName,
                                             @RequestParam String dni, @RequestParam String email,
                                             @RequestParam String password) {
        if (dni.length() != 8) {
            return new ResponseEntity<>("Just insert numbers on dni field", HttpStatus.FORBIDDEN);
        }

        if (firstName.isEmpty() || lastName.isEmpty() || dni.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data, please check all fields", HttpStatus.FORBIDDEN);
        }
        if (userRepository.findByDni(dni).isPresent()) {
            return new ResponseEntity<>("You have an user!", HttpStatus.FORBIDDEN);
        }
        User user = new User(firstName, lastName, dni, email, password);
        userRepository.save(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @PostMapping("/createUsers")
    public ResponseEntity<Object> createUsers(@RequestBody UserDTO user) {
        if (user.getDni().length() != 8) {
            return new ResponseEntity<>("Just insert numbers on dni field", HttpStatus.FORBIDDEN);
        }

        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getDni().isEmpty()
                || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing data, please check all fields", HttpStatus.FORBIDDEN);
        }
        if (userRepository.findByDni(user.getDni()).isPresent()) {
            userService.createAccountToUser(user);
            return new ResponseEntity<>("Another account created to user", HttpStatus.CREATED);
        }
        userService.createUser(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }
}
