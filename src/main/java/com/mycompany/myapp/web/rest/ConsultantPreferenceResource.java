package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ConsultantPreference;
import com.mycompany.myapp.repository.ConsultantPreferenceRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ConsultantPreference}.
 */
@RestController
@RequestMapping("/api")
public class ConsultantPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(ConsultantPreferenceResource.class);

    private static final String ENTITY_NAME = "consultantPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultantPreferenceRepository consultantPreferenceRepository;

    public ConsultantPreferenceResource(ConsultantPreferenceRepository consultantPreferenceRepository) {
        this.consultantPreferenceRepository = consultantPreferenceRepository;
    }

    /**
     * {@code POST  /consultant-preferences} : Create a new consultantPreference.
     *
     * @param consultantPreference the consultantPreference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultantPreference, or with status {@code 400 (Bad Request)} if the consultantPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consultant-preferences")
    public ResponseEntity<ConsultantPreference> createConsultantPreference(@RequestBody ConsultantPreference consultantPreference)
        throws URISyntaxException {
        log.debug("REST request to save ConsultantPreference : {}", consultantPreference);
        if (consultantPreference.getId() != null) {
            throw new BadRequestAlertException("A new consultantPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultantPreference result = consultantPreferenceRepository.save(consultantPreference);
        return ResponseEntity
            .created(new URI("/api/consultant-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /consultant-preferences/:id} : Updates an existing consultantPreference.
     *
     * @param id the id of the consultantPreference to save.
     * @param consultantPreference the consultantPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultantPreference,
     * or with status {@code 400 (Bad Request)} if the consultantPreference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultantPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consultant-preferences/{id}")
    public ResponseEntity<ConsultantPreference> updateConsultantPreference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ConsultantPreference consultantPreference
    ) throws URISyntaxException {
        log.debug("REST request to update ConsultantPreference : {}, {}", id, consultantPreference);
        if (consultantPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultantPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultantPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultantPreference result = consultantPreferenceRepository.save(consultantPreference);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultantPreference.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /consultant-preferences/:id} : Partial updates given fields of an existing consultantPreference, field will ignore if it is null
     *
     * @param id the id of the consultantPreference to save.
     * @param consultantPreference the consultantPreference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultantPreference,
     * or with status {@code 400 (Bad Request)} if the consultantPreference is not valid,
     * or with status {@code 404 (Not Found)} if the consultantPreference is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultantPreference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consultant-preferences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ConsultantPreference> partialUpdateConsultantPreference(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ConsultantPreference consultantPreference
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConsultantPreference partially : {}, {}", id, consultantPreference);
        if (consultantPreference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultantPreference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultantPreferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultantPreference> result = consultantPreferenceRepository
            .findById(consultantPreference.getId())
            .map(
                existingConsultantPreference -> {
                    if (consultantPreference.getMotivation() != null) {
                        existingConsultantPreference.setMotivation(consultantPreference.getMotivation());
                    }
                    if (consultantPreference.getCriterion() != null) {
                        existingConsultantPreference.setCriterion(consultantPreference.getCriterion());
                    }
                    if (consultantPreference.getPriority() != null) {
                        existingConsultantPreference.setPriority(consultantPreference.getPriority());
                    }

                    return existingConsultantPreference;
                }
            )
            .map(consultantPreferenceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consultantPreference.getId())
        );
    }

    /**
     * {@code GET  /consultant-preferences} : get all the consultantPreferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultantPreferences in body.
     */
    @GetMapping("/consultant-preferences")
    public List<ConsultantPreference> getAllConsultantPreferences() {
        log.debug("REST request to get all ConsultantPreferences");
        return consultantPreferenceRepository.findAll();
    }

    /**
     * {@code GET  /consultant-preferences/:id} : get the "id" consultantPreference.
     *
     * @param id the id of the consultantPreference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultantPreference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consultant-preferences/{id}")
    public ResponseEntity<ConsultantPreference> getConsultantPreference(@PathVariable String id) {
        log.debug("REST request to get ConsultantPreference : {}", id);
        Optional<ConsultantPreference> consultantPreference = consultantPreferenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(consultantPreference);
    }

    /**
     * {@code DELETE  /consultant-preferences/:id} : delete the "id" consultantPreference.
     *
     * @param id the id of the consultantPreference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consultant-preferences/{id}")
    public ResponseEntity<Void> deleteConsultantPreference(@PathVariable String id) {
        log.debug("REST request to delete ConsultantPreference : {}", id);
        consultantPreferenceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
