package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Education.class);
    }
}
