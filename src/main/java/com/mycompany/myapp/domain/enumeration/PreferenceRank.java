package com.mycompany.myapp.domain.enumeration;

/**
 * The PreferenceRank enumeration.
 */
public enum PreferenceRank {
    FIRST_CHOICE("FirstChoice"),
    SECOND_CHOICE("SecondChoice"),
    THIRD_CHOICE("ThirdChoice");

    private final String value;

    PreferenceRank(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
