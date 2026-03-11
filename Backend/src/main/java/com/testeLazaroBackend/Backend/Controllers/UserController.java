package com.testeLazaroBackend.Backend.Controllers;

import com.testeLazaroBackend.Backend.DTO.UserDTO;
import com.testeLazaroBackend.Backend.Entities.User;
import com.testeLazaroBackend.Backend.Exceptions.EmptyProfilesException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Exceptions.UserNameTooShortException;
import com.testeLazaroBackend.Backend.Exceptions.UserNotFoundException;
import com.testeLazaroBackend.Backend.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users") // rota base
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String marmelada(){
        return "marmelada";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") UUID userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(user);
    }

    @ExceptionHandler({EmptyProfilesException.class, ProfilesNotFoundException.class, UserNameTooShortException.class})
    public ResponseEntity<String> handleProfile(RuntimeException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(id,userDTO);
        return ResponseEntity.status(200).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable  UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}