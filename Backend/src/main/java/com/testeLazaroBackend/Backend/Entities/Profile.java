package com.testeLazaroBackend.Backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.testeLazaroBackend.Backend.Exceptions.ProfileDescriptionTooShortException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Profile")
public class Profile {

    @Getter
    @Setter
    @Id
    private Integer id;
    @Getter
    private String description;
    @Getter @Setter
    @ManyToMany(mappedBy = "profiles")
    private List<User> usuarios;

    public void setDescription(String description){
        if(description.length() <= 5){
            throw new ProfileDescriptionTooShortException(5);
        }
        this.description = description;
    }

}
