package com.microservices.paymentModule.controllers;

import com.microservices.paymentModule.dto.UserDTO;
import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.models.Account;
import com.microservices.paymentModule.service.IUserService;
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
    @Qualifier("userServiceFeign")
    private IUserService userService;

    @GetMapping("/list")
    public List<User> userList() {
        return userService.usersActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found or not active", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/accounts/{idAccount}")
    public List<Account> getUserByUserId(@PathVariable Long idAccount) {
        return userService.findByIdAccount(idAccount);
    }

    @PostMapping("/createUsers")
    public ResponseEntity<Object> createUsers(@RequestBody UserDTO user) throws Exception {
        try {
            if (userService.findByDni(user.getDni()) != null) {
                User userCreated = userService.createUser(user);
                return new ResponseEntity<>("Account added", HttpStatus.OK);
            }
            User userCreated = userService.createUser(user);
            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@RequestBody User user, @PathVariable Long id) throws Exception {
        try {
            User userToDelete = userService.deleteUser(user);
            return new ResponseEntity<>("User was deleted", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/editUser")
    public ResponseEntity<Object> editUser(@RequestParam Long id, @RequestBody User user) throws Exception {
        try {
            User editedUser = userService.editUser(id, user);
            return new ResponseEntity<>(editedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
