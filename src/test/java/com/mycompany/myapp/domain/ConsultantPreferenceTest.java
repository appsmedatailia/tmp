package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsultantPreferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultantPreference.class);
        ConsultantPreference consultantPreference1 = new ConsultantPreference();
        consultantPreference1.setId("id1");
        ConsultantPreference consultantPreference2 = new ConsultantPreference();
        consultantPreference2.setId(consultantPreference1.getId());
        assertThat(consultantPreference1).isEqualTo(consultantPreference2);
        consultantPreference2.setId("id2");
        assertThat(consultantPreference1).isNotEqualTo(consultantPreference2);
        consultantPreference1.setId(null);
        assertThat(consultantPreference1).isNotEqualTo(consultantPreference2);
    }
}
