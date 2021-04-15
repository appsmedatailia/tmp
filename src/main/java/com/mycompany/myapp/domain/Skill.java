package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.SkillType;
import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Skill.
 */
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("name")
    private String name;

    @Field("type")
    private SkillType type;

    @Field("proficiency")
    private String proficiency;

    @DBRef
    @Field("consultant")
    @JsonIgnoreProperties(
        value = { "user", "entreprise", "manager", "experiences", "educations", "certifications", "skills", "preferences" },
        allowSetters = true
    )
    private Consultant consultant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getName() {
        return this.name;
    }

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillType getType() {
        return this.type;
    }

    public Skill type(SkillType type) {
        this.type = type;
        return this;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public String getProficiency() {
        return this.proficiency;
    }

    public Skill proficiency(String proficiency) {
        this.proficiency = proficiency;
        return this;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public Consultant getConsultant() {
        return this.consultant;
    }

    public Skill consultant(Consultant consultant) {
        this.setConsultant(consultant);
        return this;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
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
        return "Skill{" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", proficiency='" + getProficiency() + "'" +
            "}";
    }
}
