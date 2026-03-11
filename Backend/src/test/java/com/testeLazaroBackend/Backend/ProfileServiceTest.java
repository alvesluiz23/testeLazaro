package com.testeLazaroBackend.Backend;

import com.testeLazaroBackend.Backend.DTO.ProfileDTO;
import com.testeLazaroBackend.Backend.Entities.Profile;
import com.testeLazaroBackend.Backend.Entities.User;
import com.testeLazaroBackend.Backend.Exceptions.ProfileDescriptionTooShortException;
import com.testeLazaroBackend.Backend.Exceptions.ProfileInUseException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Repositories.ProfileRepository;
import com.testeLazaroBackend.Backend.Services.ProfileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    static ProfileRepository  profileRepository;
    static  ProfileService profileService;

    @BeforeAll
    public static void prepare(){
        profileRepository = mock(ProfileRepository.class);
        profileService = new ProfileService(profileRepository);
    }

    @Test
    void shouldSaveAndReturnProfile() {
        Profile profile = new Profile();
        profile.setDescription("Administrador");

        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.createProfile(profile);

        assertEquals(profile, result);
        verify(profileRepository).save(profile);
    }

    @Test
    void shouldThrowExceptionWhenDescriptionTooShort() {
        Profile profile = new Profile();
        String shortDescription = "Admin";
        assertThrows(ProfileDescriptionTooShortException.class,
                () -> profile.setDescription(shortDescription));
    }


    @Test
    void shouldReturnProfileWhenExists() {
        Integer profileId = 1;
        Profile profile = new Profile();
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        Profile result = profileService.getProfileById(profileId);

        assertEquals(profile, result);
    }

    @Test
    void shouldThrowExceptionWhenProfileNotFoundInGet() {
        Integer profileId = 1;
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(ProfilesNotFoundException.class, () -> profileService.getProfileById(profileId));
    }

    @Test
    void shouldThrowExceptionWhenProfileNotFound() {
        Integer profileId = 1;
        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(ProfilesNotFoundException.class, () -> profileService.deleteProfile(profileId));
    }

    @Test
    void shouldThrowExceptionWhenProfileInUse() {
        Integer profileId = 1;
        Profile profile = mock(Profile.class);
        when(profile.getUsuarios()).thenReturn(List.of(new User()));
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        assertThrows(ProfileInUseException.class, () -> profileService.deleteProfile(profileId));
    }

    @Test
    void shouldDeleteProfileWhenNoUsers() {
        Integer profileId = 1;
        Profile profile = mock(Profile.class);
        when(profile.getUsuarios()).thenReturn(Collections.emptyList());
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        profileService.deleteProfile(profileId);

        verify(profileRepository).deleteById(profileId);
    }

    @Test
    void shouldUpdateProfileWhenExists() {
        Integer profileId = 1;
        ProfileDTO dto = new ProfileDTO(profileId, "Descrição Atualizada");

        Profile profile = new Profile();
        profile.setId(profileId);
        profile.setDescription("Descrição Antiga");

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile updated = profileService.updateProfile(profileId, dto);

        assertEquals(dto.description(), updated.getDescription());
        verify(profileRepository).save(profile);
    }

    @Test
    void shouldThrowExceptionWhenProfileNotFoundInUpdate() {
        Integer profileId = 1;
        ProfileDTO dto = new ProfileDTO(profileId, "Descrição Atualizada");

        when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

        assertThrows(ProfilesNotFoundException.class, () -> profileService.updateProfile(profileId, dto));
        verify(profileRepository, never()).save(any());
    }
}
