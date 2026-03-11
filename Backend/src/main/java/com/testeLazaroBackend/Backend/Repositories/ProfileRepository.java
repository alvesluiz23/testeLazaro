package com.testeLazaroBackend.Backend.Repositories;


import com.testeLazaroBackend.Backend.Entities.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {


    @Query(
            value = "SELECT COUNT(*) FROM profile WHERE id IN (:ids)",
            nativeQuery = true
    )
    long countByIdIn(List<Integer> ids);

}
