package com.testeLazaroBackend.Backend;

import com.testeLazaroBackend.Backend.DTO.UserDTO;
import com.testeLazaroBackend.Backend.Entities.Profile;
import com.testeLazaroBackend.Backend.Entities.User;
import com.testeLazaroBackend.Backend.Exceptions.EmptyProfilesException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Exceptions.UserNameTooShortException;
import com.testeLazaroBackend.Backend.Exceptions.UserNotFoundException;
import com.testeLazaroBackend.Backend.Repositories.ProfileRepository;
import com.testeLazaroBackend.Backend.Repositories.UserRepository;
import com.testeLazaroBackend.Backend.Services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static UserRepository userRepository;
    private static ProfileRepository profileRepository;
    private static UserService userService;

    @BeforeAll
    public static void setup() {
        userRepository = mock(UserRepository.class);
        profileRepository = mock(ProfileRepository.class);
        userService = new UserService(userRepository, profileRepository);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooShort() {
        UserDTO userDTO = new UserDTO(
                UUID.randomUUID(),
                "Luiz",
                List.of(10)
        );

        when(profileRepository.countByIdIn(userDTO.profileIds()))
                .thenReturn((long) userDTO.profileIds().size());
        assertThatThrownBy(() -> userService.createUser(userDTO))
                .isInstanceOf(UserNameTooShortException.class)
                .hasMessageContaining("name");
    }

    @Test
    void shouldThrowExceptionWhenProfilesDoNotExist() {
        Integer id1 = 01;
        Integer id2 = 02;
        UserDTO dto = new UserDTO(
                null,
                "Luiz Eduardo",
                List.of(id1, id2)
        );

        when(profileRepository.findAllById(dto.profileIds())).thenReturn(Arrays.asList());
        assertThrows(ProfilesNotFoundException.class, () -> {
            userService.createUser(dto);
        });
    }

    @Test
    void ShouldThrowEmptyProfilesException() {
        UserDTO dto = new UserDTO(UUID.randomUUID(), "FernandoSilva", Collections.emptyList());

        assertThrows(EmptyProfilesException.class, () -> userService.createUser(dto));
    }


    @Test
    void shouldCreateUserWhenAllProfilesExist() {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(null, "Luiz Eduardo", List.of(01, 02));

        clearInvocations(userRepository);
        clearInvocations(profileRepository);

        User savedUser = new User();
        savedUser.setName(userDTO.name());
        savedUser.setId(userId);

        Profile profile1 = new Profile();
        profile1.setId(1);
        profile1.setDescription("Admininstrador");

        Profile profile2 = new Profile();
        profile2.setId(2);
        profile2.setDescription("Desenvolvedor");

        List<Profile> mockedProfiles = Arrays.asList(profile1, profile2);

        when(profileRepository.findAllById(Arrays.asList(1,2))).thenReturn(mockedProfiles);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(userDTO);

        assertEquals("Luiz Eduardo", result.getName());
        assertEquals(userId, result.getId());

        verify(profileRepository, times(1)).findAllById(Arrays.asList(1,2));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnUserWhenFound() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);


        clearInvocations(userRepository);
        clearInvocations(profileRepository);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        clearInvocations(userRepository);
        clearInvocations(profileRepository);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldUpdateUserWhenFound() {
        clearInvocations(userRepository);
        clearInvocations(profileRepository);

        Profile profile1 = new Profile();
        profile1.setId(1);
        profile1.setDescription("Admininstrador");

        Profile profile2 = new Profile();
        profile2.setId(2);
        profile2.setDescription("Desenvolvedor");


        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(userId, "Luiz Eduardo", List.of(1, 2));
        User user = new User();
        user.setId(userId);
        user.setName("Luiz Carlos");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(profileRepository.findAllById(userDTO.profileIds())).thenReturn(Arrays.asList(profile1, profile2));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateUser(userId,userDTO);

        assertNotNull(updated);
        assertEquals("Luiz Eduardo", updated.getName());
        verify(userRepository).findById(userId);
        verify(profileRepository).findAllById(userDTO.profileIds());
        verify(userRepository).save(user);
    }


}
