package lt.golay.service;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import lt.golay.constant.Matrix;
import lt.golay.service.util.MathUtils;

/**
 * dekodavimo Golay metodu implementacija
 */
@Service
public class DecodeService {

    private static final int VECTOR_LENGTH = 12;

    public int[] decode(final int[] receivedVector) {
        int[] decodedMessage = MathUtils.addVectors(receivedVector, golayDecode(receivedVector)); // Sudedam klaidos vektoriu su gautu kodu
        return MathUtils.modulateArray(decodedMessage);
    }

    private int[] golayDecode(final int[] code) {

        int[] vectorU = MathUtils.nullVector(24);

        final int[] syndromeA = getSyndromeA(code);

        if (MathUtils.vectorWeight(syndromeA) <= 3) { //2 žingsnis
            IntStream.range(0, VECTOR_LENGTH).forEach(i -> vectorU[i] = syndromeA[i]);
            return vectorU;
        }

        if (isScenarioThree(vectorU, syndromeA)) {
            return vectorU;
        }

        final int[] syndromeB = getSyndromeB(syndromeA);
        if (MathUtils.vectorWeight(syndromeB) <= 3) {
            IntStream.range(VECTOR_LENGTH, 24).forEach(i -> vectorU[i] = syndromeB[i - VECTOR_LENGTH]);
            return vectorU;
        }

        if (isScenarioSix(vectorU, syndromeB)) {
            return vectorU;
        }
        return MathUtils.nullVector(24);
    }

    /**
     * @param vectorU   vektorius kuriuo gražinamas rezultatas jeigu pavyko dekoduoti [s+bi,e1]
     * @param syndromeA sindromasA
     * @return ar pavyko dekoduoti
     */
    private boolean isScenarioThree(final int[] vectorU, final int[] syndromeA) {
        for (int i = 0; i < Matrix.B.length; i++) {
            if (getWeightSBi(syndromeA, i) <= 2) {
                final int[] sum = Stream.of(MathUtils.getRowFromMatrix(Matrix.B, i))
                    .map(row -> MathUtils.addVectors(syndromeA, row))
                    .map(MathUtils::modulateArray)
                    .findFirst()
                    .get();
                IntStream.range(0, VECTOR_LENGTH).forEach(j -> vectorU[j] = sum[j]);
                vectorU[VECTOR_LENGTH + i] = 1; //ei žodis, nustatome pirmą bit'ą į 1
                return true;
            }
        }
        return false;
    }

    /**
     * @param vectorU   vektorius kuriuo gražinamas rezultatas
     * @param syndromeB sindromasB
     * @return ar šeštas žingsnis dekodavo
     */
    private boolean isScenarioSix(final int[] vectorU, final int[] syndromeB) {
        for (int i = 0; i < Matrix.B.length; i++) {
            final int[] row = MathUtils.getRowFromMatrix(Matrix.B, i);
            final int[] syndromeRow = MathUtils.modulateArray(MathUtils.addVectors(syndromeB, row));

            if (MathUtils.vectorWeight(syndromeRow) <= 2) {
                vectorU[i] = 1; //[ei , sB+bi]
                System.arraycopy(syndromeRow, 0, vectorU, VECTOR_LENGTH, VECTOR_LENGTH);
                return true;
            }
        }
        return false;
    }

    /**
     * @param syndromeA sindromas
     * @param i         B matricos eilutes numeris
     * @return wt(s + b1)
     */
    private int getWeightSBi(final int[] syndromeA, final int i) {
        return Stream.of(MathUtils.getRowFromMatrix(Matrix.B, i))
            .map(row -> MathUtils.addVectors(syndromeA, row))
            .map(MathUtils::modulateArray)
            .map(MathUtils::vectorWeight)
            .findFirst()
            .get();
    }

    /**
     * apskaičiuoja sindromą s = wH
     *
     * @param receivedVector gautas vektorius dekodavimui w
     * @return sindromas s = wH
     */
    private int[] getSyndromeA(final int[] receivedVector) {
        return Stream.of(receivedVector)
            .map(this::prepVectorForDecode)
            .map(MathUtils::vectorToMatrix)
            .map(matrix -> MathUtils.multiplyMatrix(matrix, Matrix.H))
            .map(MathUtils::flattenMatrix)
            .map(MathUtils::modulateArray)
            .findFirst()
            .get();
    }

    /**
     * sudaugina sindromaA su matrica B
     *
     * @param syndromeA pirmasis sindromas
     * @return sindromasb
     */
    private int[] getSyndromeB(final int[] syndromeA) {
        return Stream.of(syndromeA)
            .map(MathUtils::vectorToMatrix)
            .map(matrixA -> MathUtils.multiplyMatrix(matrixA, Matrix.B))
            .map(MathUtils::flattenMatrix)
            .map(MathUtils::modulateArray)
            .findFirst().get();
    }

    /**
     * @param encodedVector c23
     * @return prideda paskutini 24ą bit'ą, kad padaryt svori nelyginį
     */
    private int[] prepVectorForDecode(final int[] encodedVector) {
        int[] finalVector = new int[24];
        MathUtils.copyArray(finalVector, encodedVector);
        finalVector[23] = MathUtils.vectorWeight(finalVector) % 2 == 0 ? 1 : 0;
        return finalVector;
    }
}
