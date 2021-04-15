package com.mycompany.myapp.domain.enumeration;

/**
 * The Civility enumeration.
 */
public enum Civility {
    MR("Mr"),
    MRS("Mrs");

    private final String value;

    Civility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
