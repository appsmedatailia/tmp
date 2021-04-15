package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Certification.
 */
public class Certification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("issue_date")
    private LocalDate issueDate;

    @Field("end_date")
    private LocalDate endDate;

    @Field("issuing_organization")
    private String issuingOrganization;

    @Field("credential_id")
    private String credentialID;

    @Field("credential_url")
    private String credentialURL;

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

    public Certification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Certification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getIssueDate() {
        return this.issueDate;
    }

    public Certification issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Certification endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getIssuingOrganization() {
        return this.issuingOrganization;
    }

    public Certification issuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
        return this;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public String getCredentialID() {
        return this.credentialID;
    }

    public Certification credentialID(String credentialID) {
        this.credentialID = credentialID;
        return this;
    }

    public void setCredentialID(String credentialID) {
        this.credentialID = credentialID;
    }

    public String getCredentialURL() {
        return this.credentialURL;
    }

    public Certification credentialURL(String credentialURL) {
        this.credentialURL = credentialURL;
        return this;
    }

    public void setCredentialURL(String credentialURL) {
        this.credentialURL = credentialURL;
    }

    public Consultant getConsultant() {
        return this.consultant;
    }

    public Certification consultant(Consultant consultant) {
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
        if (!(o instanceof Certification)) {
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
        return "Certification{" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", issuingOrganization='" + getIssuingOrganization() + "'" +
            ", credentialID='" + getCredentialID() + "'" +
            ", credentialURL='" + getCredentialURL() + "'" +
            "}";
    }
}
