package lt.golay.service;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import com.google.common.base.Splitter;
import com.google.common.primitives.Bytes;

@Service
public class DataConverter {

    /**
     *
     * @param data duomenys String formatu
     * @return duomeyns bitų masyvas
     */
    public int[] convertToBits(final String data) {
        return Stream.of(data.getBytes(Charset.defaultCharset()))
            .map(ByteBuffer::wrap)
            .map(this::bytesToBits)
            .findFirst()
            .get();
    }

    /**
     *
     * @param byteBuffer baitų seka
     * @return bitų masyvas
     */
    public int[] bytesToBits(final ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        final StringBuilder builder = new StringBuilder();
        while (byteBuffer.hasRemaining()) {
            builder.append(String.format("%8s", Integer.toBinaryString(byteBuffer.get() & 0xFF)).replace(' ', '0'));
        }
        char[] bits = builder.toString().toCharArray();

        return IntStream.range(0, bits.length)
            .map(i -> Character.getNumericValue(bits[i]))
            .toArray();
    }

    /**
     *
     * @param bits bitų masyvas
     * @return iš bitų atgauti duomenys string formatu
     */
    public String convertToMessage(int[] bits) {
        return new String(Bytes.toArray(bitsToByte(bits, 8)), Charset.defaultCharset());
    }

    /**
     *
     * @param bits bitų masyvas kuris bus koduojamas
     * @param length ilgis į keik bitų užkoduotas baitas
     * @return atkoduotų bytų sąrašas
     */
    public List<Byte> bitsToByte(int[] bits, final int length) {
        Iterable<String> byteString = Splitter.fixedLength(length).split(getCollect(bits));

        List<Byte> bytes = new ArrayList<>();
        byteString.forEach(b -> bytes.add(bitsToByte(b)));
        return bytes;
    }

    private String getCollect(final int[] bits) {
        return Arrays.stream(bits).mapToObj(Integer::toString).collect(Collectors.joining());
    }

    private byte bitsToByte(final String bits) {
        return (byte) new BigInteger(bits, 2).intValue();
    }

    /**
     * @param data
     * @param size
     * @return sukarpo vectoriu į mažesniu masyvus kuriuos sugrąžina matricoje
     */
    public int[][] splitBits(final int[] data, final int size) {
        final int length = data.length;
        final int[][] dest = new int[(length + size - 1) / size][];
        int destIndex = 0;
        int stopIndex = 0;

        for (int startIndex = 0; startIndex + size <= length; startIndex += size) {
            stopIndex += size;
            dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
        }

        if (stopIndex < length) {
            dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);
        }
        return dest;
    }

}
