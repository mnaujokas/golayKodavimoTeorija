package lt.golay.service;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import com.google.common.primitives.Bytes;
import lt.golay.domain.Image;
import lt.golay.repository.ImageRepository;
import lt.golay.service.util.MathUtils;
import static java.lang.System.arraycopy;

/**
 * paveiksliukų apdorojimo servisas
 */
@Service
public class ImageService {

    private final DataConverter dataConverter;

    private final ChannelService channelService;

    private final ImageRepository imageRepository;
    private final BitService bitService;

    public ImageService(final DataConverter dataConverter,
        final ChannelService channelService, final ImageRepository imageRepository,
        final BitService bitService) {
        this.dataConverter = dataConverter;

        this.channelService = channelService;

        this.imageRepository = imageRepository;
        this.bitService = bitService;
    }

    public Image processImage(final Image image) {

        int[] imageBits = convertToBits(image);

        //apdorojamas paveiksliukas be kodavimo
        Stream.of(imageBits)
            .map(array -> channelService.send(array, image.getProbability()))
            .map(bytes -> convertToImage(bytes, image.getHeader()))
            .forEach(image::setNoEncoding);

        final int[][] finalMatrix = bitService.encodeSendDecode(image.getProbability(), imageBits);
        int[] flattened = MathUtils.flattenMatrix(finalMatrix);
        image.setWithEncoding(convertToImage(flattened, image.getHeader()));
        image.setNoEncodingContentType(image.getFileContentType());
        image.setWithEncodingContentType(image.getFileContentType());
        return imageRepository.save(image);
    }

    /**
     * @param image paveiksliuko forma
     * @return išsaugo paveiksliuko header ir gražina int[] bitų masyva
     */
    private int[] convertToBits(final Image image) {
        ByteBuffer buffer = ByteBuffer.wrap(image.getFile());
        image.setHeader(Arrays.copyOfRange(image.getFile(), 0, 60));
        return dataConverter.bytesToBits(buffer);
    }

    /**
     *
     * @param bits bitų int masyvas
     * @param header išsaugotas header
     * @return konvertuoja bit masyva į byte masyvą ir prisega headerį
     */
    private byte[] convertToImage(int[] bits, byte[] header) {
        byte[] byteArray = Bytes.toArray(dataConverter.bitsToByte(bits, 8));
        arraycopy(header, 0, byteArray, 0, header.length);
        return byteArray;
    }

}
