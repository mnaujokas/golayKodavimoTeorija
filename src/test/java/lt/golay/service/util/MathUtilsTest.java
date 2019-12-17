package lt.golay.service.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void testMultiplyMatrix() {
        int[][] matrixA = new int[][] {
            new int[] {1, 1, 1, 1, 1, 1, 1, 1},
            new int[] {0, 1, 1, 1, 0, 1, 0, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1},
            new int[] {0, 0, 1, 1, 0, 0, 1, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1}
        };

        int[][] matrixB = new int[][] {
            new int[] {0, 0, 1, 1, 0, 0, 1, 1},
            new int[] {1, 0, 0, 0, 1, 1, 1, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1},
            new int[] {1, 0, 0, 0, 1, 1, 1, 1},
            new int[] {1, 0, 0, 0, 1, 1, 1, 1},
            new int[] {0, 0, 0, 0, 1, 1, 1, 1},
            new int[] {1, 1, 1, 1, 1, 1, 1, 1},
            new int[] {0, 1, 0, 1, 0, 1, 0, 0}
        };
        int[][] matrixC = new int[][] {
            new int[] {4, 2, 2, 3, 6, 7, 7, 7},
            new int[] {2, 1, 0, 1, 4, 5, 4, 4},
            new int[] {2, 2, 1, 2, 3, 4, 3, 3},
            new int[] {2, 2, 1, 2, 3, 4, 3, 3},
            new int[] {2, 2, 1, 2, 3, 4, 3, 3},
            new int[] {2, 2, 1, 2, 3, 4, 3, 3},
            new int[] {2, 2, 1, 2, 3, 4, 3, 3},
            new int[] {2, 2, 1, 2, 3, 4, 3, 3}
        };
        int[][] result = MathUtils.multiplyMatrix(matrixA, matrixB);
        for (int i = 0; i < matrixA[0].length; i++) {
            assertArrayEquals(matrixC[i], result[i]);
        }
    }

    @Test
    void moduloArray() {
        int[] data = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] excpectedResult = new int[] {0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        assertArrayEquals(excpectedResult, MathUtils.modulateArray(data));
    }

    @Test
    void testAddVectors() {
        int[] vectorA = new int[] {1, 1, 1, 1, 1};
        int[] vectorB = new int[] {1, 1};
        assertArrayEquals(new int[] {2, 2}, MathUtils.addVectors(vectorA, vectorB));
    }

    @Test
    void testVectorWeight() {
        assertEquals(3, MathUtils.vectorWeight(new int[] {0, 1, 0, 1, 1}));
    }

    @Test
    void padVector() {
        assertArrayEquals(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}, MathUtils.padVector(new int[] {1, 1, 1, 1, 1, 1, 1, 1}));
    }

}
