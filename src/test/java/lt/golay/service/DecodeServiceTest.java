package lt.golay.service;

import org.junit.jupiter.api.Test;
import lt.golay.service.util.MathUtils;
import static org.junit.jupiter.api.Assertions.*;

class DecodeServiceTest {

    private final DecodeService decodeService = new DecodeService();

    @Test
    void testDecode() {
        int[] vector = new int[] {1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0};
        assertEquals("110000100000", MathUtils.arrayToString(MathUtils.truncateArray(decodeService.decode(vector), 12)));
    }

    @Test
    void testDecodeWith2Errors() {
        int[] vector = new int[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0};
        assertEquals("110000100000", MathUtils.arrayToString(MathUtils.truncateArray(decodeService.decode(vector), 12)));
    }

    @Test
    void testDecodeWith3Errors() {
        int[] vector = new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0};
        assertEquals("110000100000", MathUtils.arrayToString(MathUtils.truncateArray(decodeService.decode(vector), 12)));
    }

    @Test
    void testDecodeWith4Errors() {
        int[] vector = new int[] {1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0};
        assertNotEquals("110000100000", MathUtils.arrayToString(MathUtils.truncateArray(decodeService.decode(vector), 12)));
    }

}
