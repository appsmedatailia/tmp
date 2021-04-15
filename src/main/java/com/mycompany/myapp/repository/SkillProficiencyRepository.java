package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SkillProficiency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the SkillProficiency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillProficiencyRepository extends MongoRepository<SkillProficiency, String> {}
