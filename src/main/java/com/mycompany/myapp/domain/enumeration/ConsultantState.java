package com.mycompany.myapp.domain.enumeration;

/**
 * The ConsultantState enumeration.
 */
public enum ConsultantState {
    IN_BETWEEN_CONTRACTS("InBetweenContracts"),
    ON_ASSIGNMENT("OnAssignment"),
    NOT_APPLICABLE("NotApplicable");

    private final String value;

    ConsultantState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
