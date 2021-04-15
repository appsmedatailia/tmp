package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Consultant;
import com.mycompany.myapp.domain.enumeration.Civility;
import com.mycompany.myapp.domain.enumeration.ConsultantState;
import com.mycompany.myapp.repository.ConsultantRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ConsultantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsultantResourceIT {

    private static final Civility DEFAULT_CIVILITY = Civility.MR;
    private static final Civility UPDATED_CIVILITY = Civility.MRS;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final ConsultantState DEFAULT_STATE = ConsultantState.IN_BETWEEN_CONTRACTS;
    private static final ConsultantState UPDATED_STATE = ConsultantState.ON_ASSIGNMENT;

    private static final String ENTITY_API_URL = "/api/consultants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private MockMvc restConsultantMockMvc;

    private Consultant consultant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultant createEntity() {
        Consultant consultant = new Consultant()
            .civility(DEFAULT_CIVILITY)
            .fullName(DEFAULT_FULL_NAME)
            .phone(DEFAULT_PHONE)
            .state(DEFAULT_STATE);
        return consultant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultant createUpdatedEntity() {
        Consultant consultant = new Consultant()
            .civility(UPDATED_CIVILITY)
            .fullName(UPDATED_FULL_NAME)
            .phone(UPDATED_PHONE)
            .state(UPDATED_STATE);
        return consultant;
    }

    @BeforeEach
    public void initTest() {
        consultantRepository.deleteAll();
        consultant = createEntity();
    }

    @Test
    void createConsultant() throws Exception {
        int databaseSizeBeforeCreate = consultantRepository.findAll().size();
        // Create the Consultant
        restConsultantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isCreated());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeCreate + 1);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getCivility()).isEqualTo(DEFAULT_CIVILITY);
        assertThat(testConsultant.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testConsultant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testConsultant.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    void createConsultantWithExistingId() throws Exception {
        // Create the Consultant with an existing ID
        consultant.setId("existing_id");

        int databaseSizeBeforeCreate = consultantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllConsultants() throws Exception {
        // Initialize the database
        consultantRepository.save(consultant);

        // Get all the consultantList
        restConsultantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultant.getId())))
            .andExpect(jsonPath("$.[*].civility").value(hasItem(DEFAULT_CIVILITY.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    void getConsultant() throws Exception {
        // Initialize the database
        consultantRepository.save(consultant);

        // Get the consultant
        restConsultantMockMvc
            .perform(get(ENTITY_API_URL_ID, consultant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consultant.getId()))
            .andExpect(jsonPath("$.civility").value(DEFAULT_CIVILITY.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    void getNonExistingConsultant() throws Exception {
        // Get the consultant
        restConsultantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewConsultant() throws Exception {
        // Initialize the database
        consultantRepository.save(consultant);

        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Update the consultant
        Consultant updatedConsultant = consultantRepository.findById(consultant.getId()).get();
        updatedConsultant.civility(UPDATED_CIVILITY).fullName(UPDATED_FULL_NAME).phone(UPDATED_PHONE).state(UPDATED_STATE);

        restConsultantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConsultant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConsultant))
            )
            .andExpect(status().isOk());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getCivility()).isEqualTo(UPDATED_CIVILITY);
        assertThat(testConsultant.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testConsultant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testConsultant.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    void putNonExistingConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();
        consultant.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();
        consultant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();
        consultant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConsultantWithPatch() throws Exception {
        // Initialize the database
        consultantRepository.save(consultant);

        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Update the consultant using partial update
        Consultant partialUpdatedConsultant = new Consultant();
        partialUpdatedConsultant.setId(consultant.getId());

        partialUpdatedConsultant.fullName(UPDATED_FULL_NAME).state(UPDATED_STATE);

        restConsultantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultant))
            )
            .andExpect(status().isOk());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getCivility()).isEqualTo(DEFAULT_CIVILITY);
        assertThat(testConsultant.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testConsultant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testConsultant.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    void fullUpdateConsultantWithPatch() throws Exception {
        // Initialize the database
        consultantRepository.save(consultant);

        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Update the consultant using partial update
        Consultant partialUpdatedConsultant = new Consultant();
        partialUpdatedConsultant.setId(consultant.getId());

        partialUpdatedConsultant.civility(UPDATED_CIVILITY).fullName(UPDATED_FULL_NAME).phone(UPDATED_PHONE).state(UPDATED_STATE);

        restConsultantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultant))
            )
            .andExpect(status().isOk());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getCivility()).isEqualTo(UPDATED_CIVILITY);
        assertThat(testConsultant.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testConsultant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testConsultant.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    void patchNonExistingConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();
        consultant.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consultant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();
        consultant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();
        consultant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(consultant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConsultant() throws Exception {
        // Initialize the database
        consultantRepository.save(consultant);

        int databaseSizeBeforeDelete = consultantRepository.findAll().size();

        // Delete the consultant
        restConsultantMockMvc
            .perform(delete(ENTITY_API_URL_ID, consultant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
