package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.PreferenceRank;
import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Preference.
 */
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("prefernce")
    private String prefernce;

    @Field("rank")
    private PreferenceRank rank;

    @DBRef
    @Field("consultantPreference")
    @JsonIgnoreProperties(value = { "consultant", "preferenceLists" }, allowSetters = true)
    private ConsultantPreference consultantPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getPrefernce() {
        return this.prefernce;
    }

    public Preference prefernce(String prefernce) {
        this.prefernce = prefernce;
        return this;
    }

    public void setPrefernce(String prefernce) {
        this.prefernce = prefernce;
    }

    public PreferenceRank getRank() {
        return this.rank;
    }

    public Preference rank(PreferenceRank rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(PreferenceRank rank) {
        this.rank = rank;
    }

    public ConsultantPreference getConsultantPreference() {
        return this.consultantPreference;
    }

    public Preference consultantPreference(ConsultantPreference consultantPreference) {
        this.setConsultantPreference(consultantPreference);
        return this;
    }

    public void setConsultantPreference(ConsultantPreference consultantPreference) {
        this.consultantPreference = consultantPreference;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preference{" +
            ", prefernce='" + getPrefernce() + "'" +
            ", rank='" + getRank() + "'" +
            "}";
    }
}
