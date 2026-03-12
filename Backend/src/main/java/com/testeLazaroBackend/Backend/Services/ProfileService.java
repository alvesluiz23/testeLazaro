package com.testeLazaroBackend.Backend.Services;

import com.testeLazaroBackend.Backend.DTO.ProfileDTO;
import com.testeLazaroBackend.Backend.Entities.Profile;
import com.testeLazaroBackend.Backend.Exceptions.ProfileInUseException;
import com.testeLazaroBackend.Backend.Exceptions.ProfilesNotFoundException;
import com.testeLazaroBackend.Backend.Repositories.ProfileRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public record ProfileService(ProfileRepository profileRepository) {

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> getAll() {
        return (List<Profile>) profileRepository.findAll();
    }

    public Profile getProfileById(Integer id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfilesNotFoundException(List.of(id)));
    }

    public Profile updateProfile(Integer id, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(profileDTO.id())
                .orElseThrow(() -> new ProfilesNotFoundException(List.of(id)));
        profile.setDescription(profileDTO.description());

        return profileRepository.save(profile);
    }

    public void deleteProfile(Integer id) {
        Optional<Profile> profile = profileRepository.findById(id);

        if(profile.isEmpty()){
            throw new ProfilesNotFoundException(List.of(id));
        }

        if(!profile.get().getUsuarios().isEmpty()){
            throw new ProfileInUseException(id);
        }

        profileRepository.deleteById(id);
    }
}
