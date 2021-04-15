package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SkillProficiency;
import com.mycompany.myapp.repository.SkillProficiencyRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.SkillProficiency}.
 */
@RestController
@RequestMapping("/api")
public class SkillProficiencyResource {

    private final Logger log = LoggerFactory.getLogger(SkillProficiencyResource.class);

    private static final String ENTITY_NAME = "skillProficiency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillProficiencyRepository skillProficiencyRepository;

    public SkillProficiencyResource(SkillProficiencyRepository skillProficiencyRepository) {
        this.skillProficiencyRepository = skillProficiencyRepository;
    }

    /**
     * {@code POST  /skill-proficiencies} : Create a new skillProficiency.
     *
     * @param skillProficiency the skillProficiency to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillProficiency, or with status {@code 400 (Bad Request)} if the skillProficiency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-proficiencies")
    public ResponseEntity<SkillProficiency> createSkillProficiency(@RequestBody SkillProficiency skillProficiency)
        throws URISyntaxException {
        log.debug("REST request to save SkillProficiency : {}", skillProficiency);
        if (skillProficiency.getId() != null) {
            throw new BadRequestAlertException("A new skillProficiency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillProficiency result = skillProficiencyRepository.save(skillProficiency);
        return ResponseEntity
            .created(new URI("/api/skill-proficiencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-proficiencies/:id} : Updates an existing skillProficiency.
     *
     * @param id the id of the skillProficiency to save.
     * @param skillProficiency the skillProficiency to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillProficiency,
     * or with status {@code 400 (Bad Request)} if the skillProficiency is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillProficiency couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-proficiencies/{id}")
    public ResponseEntity<SkillProficiency> updateSkillProficiency(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SkillProficiency skillProficiency
    ) throws URISyntaxException {
        log.debug("REST request to update SkillProficiency : {}, {}", id, skillProficiency);
        if (skillProficiency.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillProficiency.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillProficiencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SkillProficiency result = skillProficiencyRepository.save(skillProficiency);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skillProficiency.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /skill-proficiencies/:id} : Partial updates given fields of an existing skillProficiency, field will ignore if it is null
     *
     * @param id the id of the skillProficiency to save.
     * @param skillProficiency the skillProficiency to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillProficiency,
     * or with status {@code 400 (Bad Request)} if the skillProficiency is not valid,
     * or with status {@code 404 (Not Found)} if the skillProficiency is not found,
     * or with status {@code 500 (Internal Server Error)} if the skillProficiency couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/skill-proficiencies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SkillProficiency> partialUpdateSkillProficiency(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SkillProficiency skillProficiency
    ) throws URISyntaxException {
        log.debug("REST request to partial update SkillProficiency partially : {}, {}", id, skillProficiency);
        if (skillProficiency.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillProficiency.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillProficiencyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SkillProficiency> result = skillProficiencyRepository
            .findById(skillProficiency.getId())
            .map(
                existingSkillProficiency -> {
                    if (skillProficiency.getName() != null) {
                        existingSkillProficiency.setName(skillProficiency.getName());
                    }
                    if (skillProficiency.getDescription() != null) {
                        existingSkillProficiency.setDescription(skillProficiency.getDescription());
                    }

                    return existingSkillProficiency;
                }
            )
            .map(skillProficiencyRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skillProficiency.getId())
        );
    }

    /**
     * {@code GET  /skill-proficiencies} : get all the skillProficiencies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillProficiencies in body.
     */
    @GetMapping("/skill-proficiencies")
    public List<SkillProficiency> getAllSkillProficiencies() {
        log.debug("REST request to get all SkillProficiencies");
        return skillProficiencyRepository.findAll();
    }

    /**
     * {@code GET  /skill-proficiencies/:id} : get the "id" skillProficiency.
     *
     * @param id the id of the skillProficiency to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillProficiency, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-proficiencies/{id}")
    public ResponseEntity<SkillProficiency> getSkillProficiency(@PathVariable String id) {
        log.debug("REST request to get SkillProficiency : {}", id);
        Optional<SkillProficiency> skillProficiency = skillProficiencyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skillProficiency);
    }

    /**
     * {@code DELETE  /skill-proficiencies/:id} : delete the "id" skillProficiency.
     *
     * @param id the id of the skillProficiency to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-proficiencies/{id}")
    public ResponseEntity<Void> deleteSkillProficiency(@PathVariable String id) {
        log.debug("REST request to delete SkillProficiency : {}", id);
        skillProficiencyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
