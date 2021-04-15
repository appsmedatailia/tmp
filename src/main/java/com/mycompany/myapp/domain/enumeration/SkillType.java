package com.mycompany.myapp.domain.enumeration;

/**
 * The SkillType enumeration.
 */
public enum SkillType {
    SOFT_SKILL("SoftSkill"),
    HARD_SKILL("HardSkill");

    private final String value;

    SkillType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
