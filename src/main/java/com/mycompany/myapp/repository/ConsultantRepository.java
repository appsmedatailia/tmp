package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Consultant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Consultant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultantRepository extends MongoRepository<Consultant, String> {}
