package com.servicecenter.cms.repository;

import com.servicecenter.cms.model.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<ApplicationUser, String> {

    //    @Query("{'username' : ?0}")
    Optional<ApplicationUser> findByUsername(String username);

    void deleteByTechnicianId(String technicianId);
}
