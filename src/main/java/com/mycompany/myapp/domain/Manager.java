package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Civility;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Manager.
 */
@Document(collection = "manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("civility")
    private Civility civility;

    @Field("full_name")
    private String fullName;

    @Field("phone")
    private String phone;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("entreprise")
    @JsonIgnoreProperties(value = { "managers", "consultants" }, allowSetters = true)
    private Entreprise entreprise;

    @DBRef
    @Field("managedConsultant")
    @JsonIgnoreProperties(
        value = { "user", "entreprise", "manager", "experiences", "educations", "certifications", "skills", "preferences" },
        allowSetters = true
    )
    private Set<Consultant> managedConsultants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Manager id(String id) {
        this.id = id;
        return this;
    }

    public Civility getCivility() {
        return this.civility;
    }

    public Manager civility(Civility civility) {
        this.civility = civility;
        return this;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Manager fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Manager phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return this.user;
    }

    public Manager user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public Manager entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Set<Consultant> getManagedConsultants() {
        return this.managedConsultants;
    }

    public Manager managedConsultants(Set<Consultant> consultants) {
        this.setManagedConsultants(consultants);
        return this;
    }

    public Manager addManagedConsultant(Consultant consultant) {
        this.managedConsultants.add(consultant);
        consultant.setManager(this);
        return this;
    }

    public Manager removeManagedConsultant(Consultant consultant) {
        this.managedConsultants.remove(consultant);
        consultant.setManager(null);
        return this;
    }

    public void setManagedConsultants(Set<Consultant> consultants) {
        if (this.managedConsultants != null) {
            this.managedConsultants.forEach(i -> i.setManager(null));
        }
        if (consultants != null) {
            consultants.forEach(i -> i.setManager(this));
        }
        this.managedConsultants = consultants;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        return id != null && id.equals(((Manager) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manager{" +
            "id=" + getId() +
            ", civility='" + getCivility() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
