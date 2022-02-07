package com.servicecenter.cms.repository;

import com.servicecenter.cms.model.Technician;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends MongoRepository<Technician, String> {

}
