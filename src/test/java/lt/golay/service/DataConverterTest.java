package lt.golay.service;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DataConverterTest {

    private DataConverter dataConverter = new DataConverter();

    @Test
    void bytesToBits() {
        byte[] bytes = {1, 0, 1};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int[] excpetected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        assertArrayEquals(excpetected, dataConverter.bytesToBits(buffer));
    }
}
