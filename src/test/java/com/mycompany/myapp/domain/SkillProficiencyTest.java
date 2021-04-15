package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkillProficiencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillProficiency.class);
        SkillProficiency skillProficiency1 = new SkillProficiency();
        skillProficiency1.setId("id1");
        SkillProficiency skillProficiency2 = new SkillProficiency();
        skillProficiency2.setId(skillProficiency1.getId());
        assertThat(skillProficiency1).isEqualTo(skillProficiency2);
        skillProficiency2.setId("id2");
        assertThat(skillProficiency1).isNotEqualTo(skillProficiency2);
        skillProficiency1.setId(null);
        assertThat(skillProficiency1).isNotEqualTo(skillProficiency2);
    }
}
