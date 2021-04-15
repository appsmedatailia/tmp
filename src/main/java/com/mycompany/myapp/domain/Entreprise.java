package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EntrepriseType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Entreprise.
 */
@Document(collection = "entreprise")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("logo")
    private byte[] logo;

    @Field("logo_content_type")
    private String logoContentType;

    @Field("type")
    private EntrepriseType type;

    @Field("presentation")
    private String presentation;

    @Field("phone")
    private String phone;

    @Field("website")
    private String website;

    @DBRef
    @Field("manager")
    @JsonIgnoreProperties(value = { "user", "entreprise", "managedConsultants" }, allowSetters = true)
    private Set<Manager> managers = new HashSet<>();

    @DBRef
    @Field("consultant")
    @JsonIgnoreProperties(
        value = { "user", "entreprise", "manager", "experiences", "educations", "certifications", "skills", "preferences" },
        allowSetters = true
    )
    private Set<Consultant> consultants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Entreprise id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Entreprise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Entreprise logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Entreprise logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public EntrepriseType getType() {
        return this.type;
    }

    public Entreprise type(EntrepriseType type) {
        this.type = type;
        return this;
    }

    public void setType(EntrepriseType type) {
        this.type = type;
    }

    public String getPresentation() {
        return this.presentation;
    }

    public Entreprise presentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getPhone() {
        return this.phone;
    }

    public Entreprise phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return this.website;
    }

    public Entreprise website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<Manager> getManagers() {
        return this.managers;
    }

    public Entreprise managers(Set<Manager> managers) {
        this.setManagers(managers);
        return this;
    }

    public Entreprise addManager(Manager manager) {
        this.managers.add(manager);
        manager.setEntreprise(this);
        return this;
    }

    public Entreprise removeManager(Manager manager) {
        this.managers.remove(manager);
        manager.setEntreprise(null);
        return this;
    }

    public void setManagers(Set<Manager> managers) {
        if (this.managers != null) {
            this.managers.forEach(i -> i.setEntreprise(null));
        }
        if (managers != null) {
            managers.forEach(i -> i.setEntreprise(this));
        }
        this.managers = managers;
    }

    public Set<Consultant> getConsultants() {
        return this.consultants;
    }

    public Entreprise consultants(Set<Consultant> consultants) {
        this.setConsultants(consultants);
        return this;
    }

    public Entreprise addConsultant(Consultant consultant) {
        this.consultants.add(consultant);
        consultant.setEntreprise(this);
        return this;
    }

    public Entreprise removeConsultant(Consultant consultant) {
        this.consultants.remove(consultant);
        consultant.setEntreprise(null);
        return this;
    }

    public void setConsultants(Set<Consultant> consultants) {
        if (this.consultants != null) {
            this.consultants.forEach(i -> i.setEntreprise(null));
        }
        if (consultants != null) {
            consultants.forEach(i -> i.setEntreprise(this));
        }
        this.consultants = consultants;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", type='" + getType() + "'" +
            ", presentation='" + getPresentation() + "'" +
            ", phone='" + getPhone() + "'" +
            ", website='" + getWebsite() + "'" +
            "}";
    }
}
