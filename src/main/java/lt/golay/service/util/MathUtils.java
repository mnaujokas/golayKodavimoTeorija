package lt.golay.service.util;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import static java.lang.System.arraycopy;

/**
 * matematinė funkcijos
 */
public class MathUtils {

    /**
     * @param matrixA matrica A
     * @param matrixB matrica B
     * @return A * B
     */
    public static int[][] multiplyMatrix(int[][] matrixA, int[][] matrixB) {

        int aRows = matrixA.length;
        int aColumns = matrixA[0].length;
        int bColumns = matrixB[0].length;
        int[][] matrixC = new int[aRows][bColumns];

        IntStream.range(0, aRows).forEach(i ->
            IntStream.range(0, bColumns).forEach(j -> matrixC[i][j] = 0));

        IntStream.range(0, aRows).forEach(
            i -> IntStream.range(0, bColumns).forEach(
                j -> IntStream.range(0, aColumns).forEach(
                    k -> matrixC[i][j] += matrixA[i][k] * matrixB[k][j])));

        return matrixC;
    }

    /**
     * @param a vektorius
     * @param b vektorius
     * @return sudėtas vektorius
     */
    public static int[] addVectors(final int[] a, final int[] b) {

        final int size = Math.min(a.length, b.length);
        final int[] result = new int[size];
        IntStream.range(0, size).forEach(i -> {
            result[i] += a[i];
            result[i] += b[i];
        });

        return result;
    }

    /**
     * @param matrix matrica
     * @param row    eilutes numeris
     * @return sugrazinta row eilutę iš matricos
     */
    public static int[] getRowFromMatrix(int[][] matrix, int row) {
        int[] result = new int[matrix[row].length];

        System.arraycopy(matrix[row], 0, result, 0, result.length);
        return result;
    }

    /**
     * @param array vektorius masyve
     * @return vektorius stringo formatu
     */
    public static String arrayToString(final int[] array) {
        StringBuilder result = new StringBuilder(StringUtils.EMPTY);
        for (final int value : array) {
            result.append(value);
        }
        return result.toString();
    }

    /**
     * @param array masyvas sveikų skaičių
     * @return pritaiko moduliavimo operacija masyvui
     */
    public static int[] modulateArray(int[] array) {
        IntStream.range(0, array.length)
            .forEach(i -> array[i] = array[i] % 2);
        return array;
    }

    /**
     * metodas apskaičiuoti vketoriaus svoriui
     *
     * @param vector vektorius
     * @return vektoriaus svoris
     */
    public static int vectorWeight(final int[] vector) {
        int result = 0;
        for (final int value : vector) {
            result = value == 1 ? result + 1 : result;
        }
        return result;
    }

    /**
     * konvertuoja vektoriu i matrica
     *
     * @param vector vektorius
     * @return dvimatis masyvas su vektoriumi
     */
    public static int[][] vectorToMatrix(int[] vector) {
        final int[][] result = new int[1][vector.length];
        IntStream.range(0, vector.length)
            .forEach(i -> result[0][i] = vector[i]);
        return result;
    }

    /**
     * @param matrix matrica
     * @return vektorius gautas iš matricos sujungiant eilute
     */
    public static int[] flattenMatrix(final int[][] matrix) {
        return Stream.of(matrix)
            .flatMapToInt(IntStream::of)
            .toArray();
    }

    public static void copyArray(int[] firstArray, int[] secondArray) {
        System.arraycopy(secondArray, 0, firstArray, 0, secondArray.length);
    }

    /**
     *
     * @param length vektoriaus ilgis
     * @return gražina vektorių užpildyta 0
     */
    public static int[] nullVector(final int length) {
        int[] result = new int[length];
        Arrays.fill(result, 0);
        return result;
    }

    /**
     *
     * @param data bitai stringe
     * @return bitų masyvas
     */
    public static int[] convertBitStringToBitArray(final String data) {
        String[] dataArray = data.split("");
        return Arrays.stream(dataArray).mapToInt(Integer::parseInt).toArray();
    }

    /**
     *
     * @param array masyvas
     * @param size dydis iki kurio sumažinti
     * @return sutrumpintas masyvas
     */
    public static int[] truncateArray(int[] array, final Integer size) {
        int[] result = new int[size];
        if (size >= 0) {
            System.arraycopy(array, 0, result, 0, size);
        }
        return result;
    }

    /**
     *
     * @param bits bitų masyvas
     * @return papildomas praplečiant 00 iš dešinės
     */
    public static int[] padVector(final int[] bits) {
        int[] padding = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        arraycopy(bits, 0, padding, 0, bits.length);
        return padding;
    }

}
