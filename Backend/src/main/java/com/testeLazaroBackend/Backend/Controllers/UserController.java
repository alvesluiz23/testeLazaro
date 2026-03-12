package com.testeLazaroBackend.Backend.Controllers;

import com.testeLazaroBackend.Backend.DTO.GetUserReturnDTO;
import com.testeLazaroBackend.Backend.DTO.ProfileDTO;
import com.testeLazaroBackend.Backend.DTO.UserDTO;
import com.testeLazaroBackend.Backend.Entities.Profile;
import com.testeLazaroBackend.Backend.Entities.User;
import com.testeLazaroBackend.Backend.Exceptions.EmptyProfilesException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Exceptions.UserNameTooShortException;
import com.testeLazaroBackend.Backend.Exceptions.UserNotFoundException;
import com.testeLazaroBackend.Backend.Services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users") // rota base
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Page<GetUserReturnDTO>> getUser(@PageableDefault(size = 5) Pageable pageable) {
        Page<User> users = userService.getUser(pageable);

        Page<GetUserReturnDTO> dtoPage = users.map(user -> {
            List<ProfileDTO> profiles = user.getProfiles()
                    .stream()
                    .map(profile -> new ProfileDTO(profile.getId(), profile.getDescription()))
                    .toList();

            return new GetUserReturnDTO(user.getId(), user.getName(), profiles);
        });

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserReturnDTO> getUser(@PathVariable("id") UUID userId) {
        User user = userService.getUser(userId);
        List<ProfileDTO> profiles = user.getProfiles().stream().map(profile ->
                new ProfileDTO(profile.getId(), profile.getDescription())).toList();
        GetUserReturnDTO getUserReturnDTO = new GetUserReturnDTO(user.getId(), user.getName(), profiles);

        return ResponseEntity.ok(getUserReturnDTO);
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
    public ResponseEntity<GetUserReturnDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(id,userDTO);

        List<ProfileDTO> profiles = user.getProfiles().stream().map(profile ->
                new ProfileDTO(profile.getId(), profile.getDescription())).toList();
        GetUserReturnDTO getUserReturnDTO = new GetUserReturnDTO(user.getId(), user.getName(), profiles);

        return ResponseEntity.status(200).body(getUserReturnDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable  UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}