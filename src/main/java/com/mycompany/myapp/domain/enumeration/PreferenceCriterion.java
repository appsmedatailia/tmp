package com.mycompany.myapp.domain.enumeration;

/**
 * The PreferenceCriterion enumeration.
 */
public enum PreferenceCriterion {
    BY_INDUSTRY_SECTOR("ByIndustrySector"),
    BY_DOMAIN("ByDomain"),
    BY_TECHNO("By_Techno"),
    BY_ENTREPRISE("ByEntreprise");

    private final String value;

    PreferenceCriterion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
