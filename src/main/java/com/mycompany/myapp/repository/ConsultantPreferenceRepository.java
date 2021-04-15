package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConsultantPreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ConsultantPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultantPreferenceRepository extends MongoRepository<ConsultantPreference, String> {}
