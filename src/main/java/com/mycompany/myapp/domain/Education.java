package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Education.
 */
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("degree")
    private String degree;

    @Field("field_of_study")
    private String fieldOfStudy;

    @Field("school")
    private String school;

    @Field("location")
    private String location;

    @Field("start_date")
    private LocalDate startDate;

    @Field("end_date")
    private LocalDate endDate;

    @Field("description")
    private String description;

    @Field("website")
    private String website;

    @DBRef
    @Field("consultant")
    @JsonIgnoreProperties(
        value = { "user", "entreprise", "manager", "experiences", "educations", "certifications", "skills", "preferences" },
        allowSetters = true
    )
    private Consultant consultant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getDegree() {
        return this.degree;
    }

    public Education degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy() {
        return this.fieldOfStudy;
    }

    public Education fieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        return this;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getSchool() {
        return this.school;
    }

    public Education school(String school) {
        this.school = school;
        return this;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocation() {
        return this.location;
    }

    public Education location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Education startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Education endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Education description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return this.website;
    }

    public Education website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Consultant getConsultant() {
        return this.consultant;
    }

    public Education consultant(Consultant consultant) {
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
        if (!(o instanceof Education)) {
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
        return "Education{" +
            ", degree='" + getDegree() + "'" +
            ", fieldOfStudy='" + getFieldOfStudy() + "'" +
            ", school='" + getSchool() + "'" +
            ", location='" + getLocation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
