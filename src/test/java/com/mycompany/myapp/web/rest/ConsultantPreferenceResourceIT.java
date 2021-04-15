package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ConsultantPreference;
import com.mycompany.myapp.domain.enumeration.PreferenceCriterion;
import com.mycompany.myapp.domain.enumeration.Priority;
import com.mycompany.myapp.repository.ConsultantPreferenceRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ConsultantPreferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsultantPreferenceResourceIT {

    private static final String DEFAULT_MOTIVATION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVATION = "BBBBBBBBBB";

    private static final PreferenceCriterion DEFAULT_CRITERION = PreferenceCriterion.BY_INDUSTRY_SECTOR;
    private static final PreferenceCriterion UPDATED_CRITERION = PreferenceCriterion.BY_DOMAIN;

    private static final Priority DEFAULT_PRIORITY = Priority.HIGH;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    private static final String ENTITY_API_URL = "/api/consultant-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConsultantPreferenceRepository consultantPreferenceRepository;

    @Autowired
    private MockMvc restConsultantPreferenceMockMvc;

    private ConsultantPreference consultantPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsultantPreference createEntity() {
        ConsultantPreference consultantPreference = new ConsultantPreference()
            .motivation(DEFAULT_MOTIVATION)
            .criterion(DEFAULT_CRITERION)
            .priority(DEFAULT_PRIORITY);
        return consultantPreference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsultantPreference createUpdatedEntity() {
        ConsultantPreference consultantPreference = new ConsultantPreference()
            .motivation(UPDATED_MOTIVATION)
            .criterion(UPDATED_CRITERION)
            .priority(UPDATED_PRIORITY);
        return consultantPreference;
    }

    @BeforeEach
    public void initTest() {
        consultantPreferenceRepository.deleteAll();
        consultantPreference = createEntity();
    }

    @Test
    void createConsultantPreference() throws Exception {
        int databaseSizeBeforeCreate = consultantPreferenceRepository.findAll().size();
        // Create the ConsultantPreference
        restConsultantPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isCreated());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultantPreference testConsultantPreference = consultantPreferenceList.get(consultantPreferenceList.size() - 1);
        assertThat(testConsultantPreference.getMotivation()).isEqualTo(DEFAULT_MOTIVATION);
        assertThat(testConsultantPreference.getCriterion()).isEqualTo(DEFAULT_CRITERION);
        assertThat(testConsultantPreference.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    void createConsultantPreferenceWithExistingId() throws Exception {
        // Create the ConsultantPreference with an existing ID
        consultantPreference.setId("existing_id");

        int databaseSizeBeforeCreate = consultantPreferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllConsultantPreferences() throws Exception {
        // Initialize the database
        consultantPreferenceRepository.save(consultantPreference);

        // Get all the consultantPreferenceList
        restConsultantPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultantPreference.getId())))
            .andExpect(jsonPath("$.[*].motivation").value(hasItem(DEFAULT_MOTIVATION.toString())))
            .andExpect(jsonPath("$.[*].criterion").value(hasItem(DEFAULT_CRITERION.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    void getConsultantPreference() throws Exception {
        // Initialize the database
        consultantPreferenceRepository.save(consultantPreference);

        // Get the consultantPreference
        restConsultantPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, consultantPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consultantPreference.getId()))
            .andExpect(jsonPath("$.motivation").value(DEFAULT_MOTIVATION.toString()))
            .andExpect(jsonPath("$.criterion").value(DEFAULT_CRITERION.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    void getNonExistingConsultantPreference() throws Exception {
        // Get the consultantPreference
        restConsultantPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewConsultantPreference() throws Exception {
        // Initialize the database
        consultantPreferenceRepository.save(consultantPreference);

        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();

        // Update the consultantPreference
        ConsultantPreference updatedConsultantPreference = consultantPreferenceRepository.findById(consultantPreference.getId()).get();
        updatedConsultantPreference.motivation(UPDATED_MOTIVATION).criterion(UPDATED_CRITERION).priority(UPDATED_PRIORITY);

        restConsultantPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConsultantPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConsultantPreference))
            )
            .andExpect(status().isOk());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
        ConsultantPreference testConsultantPreference = consultantPreferenceList.get(consultantPreferenceList.size() - 1);
        assertThat(testConsultantPreference.getMotivation()).isEqualTo(UPDATED_MOTIVATION);
        assertThat(testConsultantPreference.getCriterion()).isEqualTo(UPDATED_CRITERION);
        assertThat(testConsultantPreference.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    void putNonExistingConsultantPreference() throws Exception {
        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();
        consultantPreference.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultantPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultantPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConsultantPreference() throws Exception {
        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();
        consultantPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConsultantPreference() throws Exception {
        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();
        consultantPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConsultantPreferenceWithPatch() throws Exception {
        // Initialize the database
        consultantPreferenceRepository.save(consultantPreference);

        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();

        // Update the consultantPreference using partial update
        ConsultantPreference partialUpdatedConsultantPreference = new ConsultantPreference();
        partialUpdatedConsultantPreference.setId(consultantPreference.getId());

        partialUpdatedConsultantPreference.motivation(UPDATED_MOTIVATION);

        restConsultantPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultantPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultantPreference))
            )
            .andExpect(status().isOk());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
        ConsultantPreference testConsultantPreference = consultantPreferenceList.get(consultantPreferenceList.size() - 1);
        assertThat(testConsultantPreference.getMotivation()).isEqualTo(UPDATED_MOTIVATION);
        assertThat(testConsultantPreference.getCriterion()).isEqualTo(DEFAULT_CRITERION);
        assertThat(testConsultantPreference.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    void fullUpdateConsultantPreferenceWithPatch() throws Exception {
        // Initialize the database
        consultantPreferenceRepository.save(consultantPreference);

        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();

        // Update the consultantPreference using partial update
        ConsultantPreference partialUpdatedConsultantPreference = new ConsultantPreference();
        partialUpdatedConsultantPreference.setId(consultantPreference.getId());

        partialUpdatedConsultantPreference.motivation(UPDATED_MOTIVATION).criterion(UPDATED_CRITERION).priority(UPDATED_PRIORITY);

        restConsultantPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultantPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultantPreference))
            )
            .andExpect(status().isOk());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
        ConsultantPreference testConsultantPreference = consultantPreferenceList.get(consultantPreferenceList.size() - 1);
        assertThat(testConsultantPreference.getMotivation()).isEqualTo(UPDATED_MOTIVATION);
        assertThat(testConsultantPreference.getCriterion()).isEqualTo(UPDATED_CRITERION);
        assertThat(testConsultantPreference.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    void patchNonExistingConsultantPreference() throws Exception {
        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();
        consultantPreference.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultantPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consultantPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConsultantPreference() throws Exception {
        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();
        consultantPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConsultantPreference() throws Exception {
        int databaseSizeBeforeUpdate = consultantPreferenceRepository.findAll().size();
        consultantPreference.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultantPreference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsultantPreference in the database
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConsultantPreference() throws Exception {
        // Initialize the database
        consultantPreferenceRepository.save(consultantPreference);

        int databaseSizeBeforeDelete = consultantPreferenceRepository.findAll().size();

        // Delete the consultantPreference
        restConsultantPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, consultantPreference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsultantPreference> consultantPreferenceList = consultantPreferenceRepository.findAll();
        assertThat(consultantPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
