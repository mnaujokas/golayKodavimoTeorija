package lt.golay.service;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import lt.golay.domain.Text;
import lt.golay.repository.TextRepository;

/**
 * teksto apdorojimo servisas
 */
@Service
public class TextService {

    private final ChannelService channelService;
    private final TextRepository textRepository;
    private final DataConverter dataConverter;
    private final BitService bitService;

    public TextService(final ChannelService channelService,
        final TextRepository textRepository, final DataConverter dataConverter, final BitService bitService) {
        this.channelService = channelService;
        this.textRepository = textRepository;
        this.dataConverter = dataConverter;
        this.bitService = bitService;
    }

    public Text parseText(final Text text) {

        final int[] message = dataConverter.convertToBits(text.getData());

        Stream.of(message).map(array -> channelService.send(array, text.getProbability()))
            .map(dataConverter::convertToMessage)
            .forEach(text::setNoEncoding);

        final StringBuilder result = new StringBuilder();
        final int[][] finalMatrix = bitService.encodeSendDecode(text.getProbability(), message);
        IntStream.range(0, finalMatrix.length)
            .forEach(i -> result.append(dataConverter.convertToMessage(finalMatrix[i])));
        text.setDecoded(result.toString());
        return textRepository.save(text);
    }
}
