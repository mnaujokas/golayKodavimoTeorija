package lt.golay.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class EncodeServiceTest {

    private final EncodeService encodeService = new EncodeService();

    @Test
    void encode() {
        assertArrayEquals(new int[] {1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0}, encodeService.encode("110000100000"));
    }
}
