package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Experience.
 */
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("job_title")
    private String jobTitle;

    @Field("entreprise")
    private String entreprise;

    @Field("location")
    private String location;

    @Field("start_date")
    private LocalDate startDate;

    @Field("end_date")
    private LocalDate endDate;

    @Field("job_context")
    private String jobContext;

    @Field("key_achievement")
    private String keyAchievement;

    @Field("key_responsibility")
    private String keyResponsibility;

    @DBRef
    @Field("consultant")
    @JsonIgnoreProperties(
        value = { "user", "entreprise", "manager", "experiences", "educations", "certifications", "skills", "preferences" },
        allowSetters = true
    )
    private Consultant consultant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getJobTitle() {
        return this.jobTitle;
    }

    public Experience jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEntreprise() {
        return this.entreprise;
    }

    public Experience entreprise(String entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getLocation() {
        return this.location;
    }

    public Experience location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Experience startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Experience endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getJobContext() {
        return this.jobContext;
    }

    public Experience jobContext(String jobContext) {
        this.jobContext = jobContext;
        return this;
    }

    public void setJobContext(String jobContext) {
        this.jobContext = jobContext;
    }

    public String getKeyAchievement() {
        return this.keyAchievement;
    }

    public Experience keyAchievement(String keyAchievement) {
        this.keyAchievement = keyAchievement;
        return this;
    }

    public void setKeyAchievement(String keyAchievement) {
        this.keyAchievement = keyAchievement;
    }

    public String getKeyResponsibility() {
        return this.keyResponsibility;
    }

    public Experience keyResponsibility(String keyResponsibility) {
        this.keyResponsibility = keyResponsibility;
        return this;
    }

    public void setKeyResponsibility(String keyResponsibility) {
        this.keyResponsibility = keyResponsibility;
    }

    public Consultant getConsultant() {
        return this.consultant;
    }

    public Experience consultant(Consultant consultant) {
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
        if (!(o instanceof Experience)) {
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
        return "Experience{" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", entreprise='" + getEntreprise() + "'" +
            ", location='" + getLocation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", jobContext='" + getJobContext() + "'" +
            ", keyAchievement='" + getKeyAchievement() + "'" +
            ", keyResponsibility='" + getKeyResponsibility() + "'" +
            "}";
    }
}
