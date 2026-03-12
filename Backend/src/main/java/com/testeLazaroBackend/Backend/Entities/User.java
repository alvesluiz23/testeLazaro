package com.testeLazaroBackend.Backend.Entities;

import com.testeLazaroBackend.Backend.Exceptions.UserNameTooShortException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name ="users")
public class User {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    private String name;
    @Getter @Setter
    @ManyToMany
    @JoinTable(
            name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> profiles;

    public void setName(String name) {
        if (name == null || name.trim().length() < 10) {
            throw new UserNameTooShortException(10);
        }
        this.name = name;
    }
}
