package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Civility;
import com.mycompany.myapp.domain.enumeration.ConsultantState;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Consultant.
 */
@Document(collection = "consultant")
public class Consultant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("civility")
    private Civility civility;

    @Field("full_name")
    private String fullName;

    @Field("phone")
    private String phone;

    @Field("state")
    private ConsultantState state;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("entreprise")
    @JsonIgnoreProperties(value = { "managers", "consultants" }, allowSetters = true)
    private Entreprise entreprise;

    @DBRef
    @Field("manager")
    @JsonIgnoreProperties(value = { "user", "entreprise", "managedConsultants" }, allowSetters = true)
    private Manager manager;

    @Field("experience")
    @JsonIgnoreProperties(value = { "consultant" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @Field("education")
    @JsonIgnoreProperties(value = { "consultant" }, allowSetters = true)
    private Set<Education> educations = new HashSet<>();

    @Field("certifications")
    @JsonIgnoreProperties(value = { "consultant" }, allowSetters = true)
    private Set<Certification> certifications = new HashSet<>();

    @Field("skills")
    @JsonIgnoreProperties(value = { "consultant" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

    @DBRef
    @Field("preference")
    @JsonIgnoreProperties(value = { "consultant", "preferenceLists" }, allowSetters = true)
    private Set<ConsultantPreference> preferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Consultant id(String id) {
        this.id = id;
        return this;
    }

    public Civility getCivility() {
        return this.civility;
    }

    public Consultant civility(Civility civility) {
        this.civility = civility;
        return this;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Consultant fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Consultant phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ConsultantState getState() {
        return this.state;
    }

    public Consultant state(ConsultantState state) {
        this.state = state;
        return this;
    }

    public void setState(ConsultantState state) {
        this.state = state;
    }

    public User getUser() {
        return this.user;
    }

    public Consultant user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public Consultant entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Manager getManager() {
        return this.manager;
    }

    public Consultant manager(Manager manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public Consultant experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Consultant addExperience(Experience experience) {
        this.experiences.add(experience);
        return this;
    }

    public Consultant removeExperience(Experience experience) {
        this.experiences.remove(experience);
        return this;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setConsultant(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setConsultant(this));
        }
        this.experiences = experiences;
    }

    public Set<Education> getEducations() {
        return this.educations;
    }

    public Consultant educations(Set<Education> educations) {
        this.setEducations(educations);
        return this;
    }

    public Consultant addEducation(Education education) {
        this.educations.add(education);
        return this;
    }

    public Consultant removeEducation(Education education) {
        this.educations.remove(education);
        return this;
    }

    public void setEducations(Set<Education> educations) {
        if (this.educations != null) {
            this.educations.forEach(i -> i.setConsultant(null));
        }
        if (educations != null) {
            educations.forEach(i -> i.setConsultant(this));
        }
        this.educations = educations;
    }

    public Set<Certification> getCertifications() {
        return this.certifications;
    }

    public Consultant certifications(Set<Certification> certifications) {
        this.setCertifications(certifications);
        return this;
    }

    public Consultant addCertifications(Certification certification) {
        this.certifications.add(certification);
        return this;
    }

    public Consultant removeCertifications(Certification certification) {
        this.certifications.remove(certification);
        return this;
    }

    public void setCertifications(Set<Certification> certifications) {
        if (this.certifications != null) {
            this.certifications.forEach(i -> i.setConsultant(null));
        }
        if (certifications != null) {
            certifications.forEach(i -> i.setConsultant(this));
        }
        this.certifications = certifications;
    }

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public Consultant skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    public Consultant addSkills(Skill skill) {
        this.skills.add(skill);
        return this;
    }

    public Consultant removeSkills(Skill skill) {
        this.skills.remove(skill);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setConsultant(null));
        }
        if (skills != null) {
            skills.forEach(i -> i.setConsultant(this));
        }
        this.skills = skills;
    }

    public Set<ConsultantPreference> getPreferences() {
        return this.preferences;
    }

    public Consultant preferences(Set<ConsultantPreference> consultantPreferences) {
        this.setPreferences(consultantPreferences);
        return this;
    }

    public Consultant addPreference(ConsultantPreference consultantPreference) {
        this.preferences.add(consultantPreference);
        consultantPreference.setConsultant(this);
        return this;
    }

    public Consultant removePreference(ConsultantPreference consultantPreference) {
        this.preferences.remove(consultantPreference);
        consultantPreference.setConsultant(null);
        return this;
    }

    public void setPreferences(Set<ConsultantPreference> consultantPreferences) {
        if (this.preferences != null) {
            this.preferences.forEach(i -> i.setConsultant(null));
        }
        if (consultantPreferences != null) {
            consultantPreferences.forEach(i -> i.setConsultant(this));
        }
        this.preferences = consultantPreferences;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consultant)) {
            return false;
        }
        return id != null && id.equals(((Consultant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consultant{" +
            "id=" + getId() +
            ", civility='" + getCivility() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
