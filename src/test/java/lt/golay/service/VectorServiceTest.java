package lt.golay.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lt.golay.GolayApp;
import lt.golay.domain.Vector;
import lt.golay.service.util.MathUtils;

@SpringBootTest(classes = GolayApp.class)
class VectorServiceTest {

    @Autowired
    private VectorService vectorService;

    @Test
    void testEncodingPerformance() {
        Map<Double, Integer> map = new HashMap<>();
        double probability = 0.001;
        IntStream.range(1, 500).forEach(i -> map.put(probability * i, getNoOfErrors(probability * i)));
        map.forEach((key, value) -> System.out.println(key + " " + value));
    }

    private Integer getNoOfErrors(final double probability) {
        String data = String.format("%12s", Integer.toBinaryString(getRandomNumberInRange(0, 245) & 0xFF)).replace(' ', '0');
        final Vector vector = new Vector();
        vector.setData(data);
        vector.setProbability(probability);
        vectorService.parseVector(vector);
        return countErrors(vector);

    }

    private int getRandomNumberInRange(int min, int max) {
        final Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

    }

    private Integer countErrors(final Vector vector) {
        Integer total = 0;
        int[] originalVector = MathUtils.convertBitStringToBitArray(vector.getData());
        int[] modifiedVector = MathUtils.convertBitStringToBitArray(vector.getDecoded());
        for (int i = 0; i < originalVector.length; i++) {
            if (originalVector[i] != modifiedVector[i]) {
                total++;
            }
        }
        return total;
    }
}
