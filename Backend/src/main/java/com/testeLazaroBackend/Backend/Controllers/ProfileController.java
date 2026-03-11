package com.testeLazaroBackend.Backend.Controllers;

import com.testeLazaroBackend.Backend.DTO.ProfileDTO;
import com.testeLazaroBackend.Backend.DTO.ReturnProfileDTO;
import com.testeLazaroBackend.Backend.DTO.ReturnUserDTO;
import com.testeLazaroBackend.Backend.DTO.UpdateDTO;
import com.testeLazaroBackend.Backend.Entities.Profile;
import com.testeLazaroBackend.Backend.Exceptions.ProfileDescriptionTooShortException;
import com.testeLazaroBackend.Backend.Exceptions.ProfileInUseException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnProfileDTO> getProfile(@PathVariable Integer id) {
        Profile profile = profileService.getProfileById(id);
        List<ReturnUserDTO> returnUserDTOList = profile.getUsuarios().stream().map(user
                -> new ReturnUserDTO(user.getId(), user.getName())).toList();

        ReturnProfileDTO profileDTO = new ReturnProfileDTO(profile.getId(),profile.getDescription(), returnUserDTOList);
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping
    public ResponseEntity<ReturnProfileDTO> createProfile(@RequestBody ProfileDTO profileDTO) {

        Profile profile = new Profile();
        profile.setDescription(profileDTO.description());
        profile.setId(profileDTO.id());
        Profile created = profileService.createProfile(profile);


        ReturnProfileDTO returnProfileDTO = new ReturnProfileDTO(created.getId(),created.getDescription(), List.of());

        return ResponseEntity.status(201).body(returnProfileDTO);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ReturnProfileDTO> updateProfile(@PathVariable Integer profileId,@RequestBody UpdateDTO updateDTO) {

        ProfileDTO profileDTO = new ProfileDTO(profileId, updateDTO.description());

        Profile updated = profileService.updateProfile(profileId,profileDTO);

        List<ReturnUserDTO> returnUserDTOList = updated.getUsuarios().stream().map(user
                -> new ReturnUserDTO(user.getId(), user.getName())).toList();

        ReturnProfileDTO returnProfileDTO = new ReturnProfileDTO(updated.getId(),updated.getDescription(), List.of());

        return ResponseEntity.status(200).body(returnProfileDTO);
    }

    @ExceptionHandler({ProfilesNotFoundException.class})
    public ResponseEntity<String> handleProfile(RuntimeException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler({ProfileDescriptionTooShortException.class, ProfileInUseException.class})
    public ResponseEntity<String> handleProfileWrongPauload(RuntimeException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Integer profileId) {
        profileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build();
    }



}
