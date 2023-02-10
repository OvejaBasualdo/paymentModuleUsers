package com.microservices.paymentModule.controllers;

import com.microservices.paymentModule.dto.UserDTO;
import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.models.Account;
import com.microservices.paymentModule.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("userServiceFeign")
    private IUserService userService;

    @Value("${configuracion.texto}")
    private String texto;
    @Autowired
    private Environment env;

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfiguracion(@Value("${server.port}") String puerto) {
        log.info(texto);
        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }

            return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

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
