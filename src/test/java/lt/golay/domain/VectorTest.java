package lt.golay.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import lt.golay.web.rest.TestUtil;

public class VectorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vector.class);
        Vector vector1 = new Vector();
        vector1.setId(1L);
        Vector vector2 = new Vector();
        vector2.setId(vector1.getId());
        assertThat(vector1).isEqualTo(vector2);
        vector2.setId(2L);
        assertThat(vector1).isNotEqualTo(vector2);
        vector1.setId(null);
        assertThat(vector1).isNotEqualTo(vector2);
    }
}
