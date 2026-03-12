package com.testeLazaroBackend.Backend.Services;

import com.testeLazaroBackend.Backend.DTO.ProfileDTO;
import com.testeLazaroBackend.Backend.DTO.UserDTO;
import com.testeLazaroBackend.Backend.Entities.Profile;
import com.testeLazaroBackend.Backend.Entities.User;
import com.testeLazaroBackend.Backend.Exceptions.EmptyProfilesException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Exceptions.UserNotFoundException;
import com.testeLazaroBackend.Backend.Repositories.ProfileRepository;
import com.testeLazaroBackend.Backend.Repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public record UserService(UserRepository userRepository, ProfileRepository profileRepository) {

    public Page<User> getUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private List<Profile> transformIdInProfile(UserDTO userDTO){
        List<Profile> profilesFound = (List<Profile>) profileRepository.findAllById(userDTO.profiles().
                stream().map(ProfileDTO::id).toList());
        if(profilesFound.size() != userDTO.profiles().size()){
            var idsFounds = profilesFound.stream().mapToInt(Profile::getId).boxed().toList();
            throw new ProfilesNotFoundException(
                    userDTO.profiles().stream().filter(profile->!idsFounds.contains(profile.id()))
                            .map(ProfileDTO::id).toList()
            );
        }
        return profilesFound;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        if(userDTO.profiles().isEmpty()){
            throw new EmptyProfilesException();
        }
        user.setProfiles(transformIdInProfile(userDTO));

        return userRepository.save(user);
    }

    public User updateUser(UUID id, UserDTO userDTO) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(userDTO.id()));

        if(userDTO.profiles().isEmpty()){
            throw new EmptyProfilesException();
        }
        user.setProfiles(transformIdInProfile(userDTO));
        user.setName(userDTO.name());

        return userRepository.save(user);
    }

    public void deleteUserById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
    }
}
