package com.mycompany.myapp.domain.enumeration;

/**
 * The EntrepriseType enumeration.
 */
public enum EntrepriseType {
    SERVICE_PROVIDER("ServiceProvider"),
    CLIENT("Client");

    private final String value;

    EntrepriseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
