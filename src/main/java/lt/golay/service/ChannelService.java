package lt.golay.service;

import org.springframework.stereotype.Service;

/**
 * nesaugaus kanalo implementacija
 */
@Service
public class ChannelService {
    /**
     *
     * @param data duomenys
     * @param probability klaidos tikimybe
     * @return grazina iš kanalo gautą seką
     */
    public int[] send(final int[] data, final Double probability) {
        int[] sent = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            sent[i] = errorOccurred(probability) ? (data[i] + 1) % 2 : data[i];
        }
        return sent;
    }

    private boolean errorOccurred(final Double probability) {
        return Math.random() < probability;
    }
}
