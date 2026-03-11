package com.testeLazaroBackend.Backend.Repositories;


import com.testeLazaroBackend.Backend.Entities.User;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

}
