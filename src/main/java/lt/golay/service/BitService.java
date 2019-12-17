package lt.golay.service;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import lt.golay.service.util.MathUtils;

@Service
public class BitService {
    private final DataConverter dataConverter;
    private final ChannelService channelService;
    private final DecodeService decodeService;
    private final EncodeService encodeService;

    public BitService(final DataConverter dataConverter, final ChannelService channelService,
        final DecodeService decodeService, final EncodeService encodeService) {
        this.dataConverter = dataConverter;
        this.channelService = channelService;
        this.decodeService = decodeService;
        this.encodeService = encodeService;
    }

    /**
     * atlieka bitų sukarpymą į reikiamio ilgio vektorius, užkoduoja, siunčia kanalu ir dekoduoja
     * @param probability kanalo iškraipymo tikimybė
     * @param bits koduojami bitai
     * @return matrica su dekoduotais bitais
     */
    public int[][] encodeSendDecode(final Double probability, final int[] bits) {
        final int[][] bytes = dataConverter.splitBits(bits, 8);
        final int[][] encoded = new int[bytes.length][23];
        IntStream.range(0, bytes.length)
            .forEach(i -> encoded[i] = encode(bytes[i]));

        final int[][] finalMatrix = new int[encoded.length][24];

        for (int i = 0; i < encoded.length; i++) {
            finalMatrix[i] = channelService.send(encoded[i], probability);
            finalMatrix[i] = MathUtils.truncateArray(decodeService.decode(finalMatrix[i]), 8);
        }
        return finalMatrix;
    }

    /**
     * @param message vektorius
     * @return vektoriu prailgina iki 12 ir uzkoduoja
     */
    private int[] encode(int[] message) {
        return Stream.of(message)
            .map(MathUtils::padVector)
            .map(encodeService::encode)
            .findFirst()
            .get();
    }
}
