package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.PreferenceCriterion;
import com.mycompany.myapp.domain.enumeration.Priority;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ConsultantPreference.
 */
@Document(collection = "consultant_preference")
public class ConsultantPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("motivation")
    private String motivation;

    @Field("criterion")
    private PreferenceCriterion criterion;

    @Field("priority")
    private Priority priority;

    @DBRef
    @Field("consultant")
    @JsonIgnoreProperties(
        value = { "user", "entreprise", "manager", "experiences", "educations", "certifications", "skills", "preferences" },
        allowSetters = true
    )
    private Consultant consultant;

    @Field("preferenceList")
    @JsonIgnoreProperties(value = { "consultantPreference" }, allowSetters = true)
    private Set<Preference> preferenceLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConsultantPreference id(String id) {
        this.id = id;
        return this;
    }

    public String getMotivation() {
        return this.motivation;
    }

    public ConsultantPreference motivation(String motivation) {
        this.motivation = motivation;
        return this;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public PreferenceCriterion getCriterion() {
        return this.criterion;
    }

    public ConsultantPreference criterion(PreferenceCriterion criterion) {
        this.criterion = criterion;
        return this;
    }

    public void setCriterion(PreferenceCriterion criterion) {
        this.criterion = criterion;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public ConsultantPreference priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Consultant getConsultant() {
        return this.consultant;
    }

    public ConsultantPreference consultant(Consultant consultant) {
        this.setConsultant(consultant);
        return this;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Set<Preference> getPreferenceLists() {
        return this.preferenceLists;
    }

    public ConsultantPreference preferenceLists(Set<Preference> preferences) {
        this.setPreferenceLists(preferences);
        return this;
    }

    public ConsultantPreference addPreferenceList(Preference preference) {
        this.preferenceLists.add(preference);
        return this;
    }

    public ConsultantPreference removePreferenceList(Preference preference) {
        this.preferenceLists.remove(preference);
        return this;
    }

    public void setPreferenceLists(Set<Preference> preferences) {
        if (this.preferenceLists != null) {
            this.preferenceLists.forEach(i -> i.setConsultantPreference(null));
        }
        if (preferences != null) {
            preferences.forEach(i -> i.setConsultantPreference(this));
        }
        this.preferenceLists = preferences;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultantPreference)) {
            return false;
        }
        return id != null && id.equals(((ConsultantPreference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultantPreference{" +
            "id=" + getId() +
            ", motivation='" + getMotivation() + "'" +
            ", criterion='" + getCriterion() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
