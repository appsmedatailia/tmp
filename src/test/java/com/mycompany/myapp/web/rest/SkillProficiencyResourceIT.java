package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SkillProficiency;
import com.mycompany.myapp.repository.SkillProficiencyRepository;
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
 * Integration tests for the {@link SkillProficiencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillProficiencyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/skill-proficiencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SkillProficiencyRepository skillProficiencyRepository;

    @Autowired
    private MockMvc restSkillProficiencyMockMvc;

    private SkillProficiency skillProficiency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillProficiency createEntity() {
        SkillProficiency skillProficiency = new SkillProficiency().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return skillProficiency;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillProficiency createUpdatedEntity() {
        SkillProficiency skillProficiency = new SkillProficiency().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return skillProficiency;
    }

    @BeforeEach
    public void initTest() {
        skillProficiencyRepository.deleteAll();
        skillProficiency = createEntity();
    }

    @Test
    void createSkillProficiency() throws Exception {
        int databaseSizeBeforeCreate = skillProficiencyRepository.findAll().size();
        // Create the SkillProficiency
        restSkillProficiencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isCreated());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeCreate + 1);
        SkillProficiency testSkillProficiency = skillProficiencyList.get(skillProficiencyList.size() - 1);
        assertThat(testSkillProficiency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillProficiency.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createSkillProficiencyWithExistingId() throws Exception {
        // Create the SkillProficiency with an existing ID
        skillProficiency.setId("existing_id");

        int databaseSizeBeforeCreate = skillProficiencyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillProficiencyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSkillProficiencies() throws Exception {
        // Initialize the database
        skillProficiencyRepository.save(skillProficiency);

        // Get all the skillProficiencyList
        restSkillProficiencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillProficiency.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    void getSkillProficiency() throws Exception {
        // Initialize the database
        skillProficiencyRepository.save(skillProficiency);

        // Get the skillProficiency
        restSkillProficiencyMockMvc
            .perform(get(ENTITY_API_URL_ID, skillProficiency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skillProficiency.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    void getNonExistingSkillProficiency() throws Exception {
        // Get the skillProficiency
        restSkillProficiencyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewSkillProficiency() throws Exception {
        // Initialize the database
        skillProficiencyRepository.save(skillProficiency);

        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();

        // Update the skillProficiency
        SkillProficiency updatedSkillProficiency = skillProficiencyRepository.findById(skillProficiency.getId()).get();
        updatedSkillProficiency.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSkillProficiencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSkillProficiency.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSkillProficiency))
            )
            .andExpect(status().isOk());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
        SkillProficiency testSkillProficiency = skillProficiencyList.get(skillProficiencyList.size() - 1);
        assertThat(testSkillProficiency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillProficiency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingSkillProficiency() throws Exception {
        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();
        skillProficiency.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillProficiencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillProficiency.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSkillProficiency() throws Exception {
        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();
        skillProficiency.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillProficiencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSkillProficiency() throws Exception {
        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();
        skillProficiency.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillProficiencyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSkillProficiencyWithPatch() throws Exception {
        // Initialize the database
        skillProficiencyRepository.save(skillProficiency);

        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();

        // Update the skillProficiency using partial update
        SkillProficiency partialUpdatedSkillProficiency = new SkillProficiency();
        partialUpdatedSkillProficiency.setId(skillProficiency.getId());

        partialUpdatedSkillProficiency.description(UPDATED_DESCRIPTION);

        restSkillProficiencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillProficiency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkillProficiency))
            )
            .andExpect(status().isOk());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
        SkillProficiency testSkillProficiency = skillProficiencyList.get(skillProficiencyList.size() - 1);
        assertThat(testSkillProficiency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillProficiency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateSkillProficiencyWithPatch() throws Exception {
        // Initialize the database
        skillProficiencyRepository.save(skillProficiency);

        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();

        // Update the skillProficiency using partial update
        SkillProficiency partialUpdatedSkillProficiency = new SkillProficiency();
        partialUpdatedSkillProficiency.setId(skillProficiency.getId());

        partialUpdatedSkillProficiency.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSkillProficiencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillProficiency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkillProficiency))
            )
            .andExpect(status().isOk());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
        SkillProficiency testSkillProficiency = skillProficiencyList.get(skillProficiencyList.size() - 1);
        assertThat(testSkillProficiency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillProficiency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingSkillProficiency() throws Exception {
        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();
        skillProficiency.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillProficiencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skillProficiency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSkillProficiency() throws Exception {
        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();
        skillProficiency.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillProficiencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSkillProficiency() throws Exception {
        int databaseSizeBeforeUpdate = skillProficiencyRepository.findAll().size();
        skillProficiency.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillProficiencyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillProficiency))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillProficiency in the database
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSkillProficiency() throws Exception {
        // Initialize the database
        skillProficiencyRepository.save(skillProficiency);

        int databaseSizeBeforeDelete = skillProficiencyRepository.findAll().size();

        // Delete the skillProficiency
        restSkillProficiencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, skillProficiency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillProficiency> skillProficiencyList = skillProficiencyRepository.findAll();
        assertThat(skillProficiencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
