package lt.golay.service;

import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import lt.golay.constant.Matrix;
import lt.golay.service.util.MathUtils;

/**
 * servisas atsakingas už vektoriu kodavima
 */
@Service
public class EncodeService {
    /**
     * @param data bitų seka string formatu
     * @return užkoduotas bitų vektorius
     */
    public int[] encode(final String data) {
        return Stream.of(data).map(MathUtils::convertBitStringToBitArray)
            .map(this::encode)
            .findFirst()
            .get();
    }

    /**
     * @param data bitų masyvvas [12]
     * @return užkoduotas vektorius [23]
     */
    public int[] encode(int[] data) {
        return Stream.of(data)
            .map(MathUtils::vectorToMatrix)
            .map(matrix -> MathUtils.multiplyMatrix(matrix, Matrix.G11))
            .map(MathUtils::flattenMatrix)
            .map(MathUtils::modulateArray)
            .findFirst()
            .get();
    }

}
