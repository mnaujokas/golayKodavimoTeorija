package lt.golay.service;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import lt.golay.domain.Vector;
import lt.golay.repository.VectorRepository;
import lt.golay.service.util.MathUtils;

/**
 * vektoriaus apdorojimo servisas
 */
@Service
public class VectorService {

    private final EncodeService encodeService;
    private final ChannelService channelService;
    private final DecodeService decodeService;
    private final VectorRepository vectorRepository;

    public VectorService(final EncodeService encodeService, final ChannelService channelService,
        final DecodeService decodeService, final VectorRepository vectorRepository) {
        this.encodeService = encodeService;
        this.channelService = channelService;
        this.decodeService = decodeService;
        this.vectorRepository = vectorRepository;
    }

    public Vector parseVector(final Vector vector) {
        final int[] encodedVector = encodeService.encode(vector.getData());
        vector.setEncoded(MathUtils.arrayToString(encodedVector));

        final int[] transferredVector = channelService.send(encodedVector, vector.getProbability());
        vector.setTransfered(MathUtils.arrayToString(transferredVector));
        vector.setErrors(parseErrors(encodedVector, transferredVector));

        final int[] decodedVector = decodeService.decode(transferredVector);

        Optional.ofNullable(decodedVector)
            .map(rawVector -> MathUtils.truncateArray(rawVector, 12))
            .map(MathUtils::arrayToString)
            .ifPresent(vector::setDecoded);

        return vectorRepository.save(vector);
    }

    /**
     * metodas atsakingas už kanalu perduoto vektoriaus pakeitimą
     *
     * @param vector vektoriaus forma
     * @return atnaujintas vektorius
     */
    public Vector updateVector(final Vector vector) {
        Stream.of(vector.getTransfered())
            .map(MathUtils::convertBitStringToBitArray)
            .map(decodeService::decode)
            .map(decodedVector -> MathUtils.truncateArray(decodedVector, 12))
            .map(MathUtils::arrayToString)
            .forEach(vector::setDecoded);

        vector.setErrors(
            parseErrors(MathUtils.convertBitStringToBitArray(vector.getEncoded()), MathUtils.convertBitStringToBitArray(vector.getTransfered())));

        return vectorRepository.save(vector);
    }

    /**
     * @param originalVector išsiųstas vektorius
     * @param modifiedVector kanalu gautas vektorius
     * @return klaidos
     */
    private String parseErrors(final int[] originalVector, final int[] modifiedVector) {
        final StringBuilder message = new StringBuilder();
        int total = 0;
        for (int i = 0; i < originalVector.length; i++) {
            if (originalVector[i] != modifiedVector[i]) {
                message.append(i).append(" ");
                total++;
            }
        }
        return message.append("Total: ").append(total).toString();
    }

}
