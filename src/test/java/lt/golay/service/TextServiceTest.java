package lt.golay.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lt.golay.GolayApp;
import lt.golay.domain.Text;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GolayApp.class)
class TextServiceTest {
    private static final String DEFAULT_DATA = "tekstas";
    private static final Double ZERO_PROBABILITY = 0.0;

    @Autowired
    private TextService textService;


    @Test
    void parseText() {
        final Text rawText = new Text()
                .data(DEFAULT_DATA)
                .probability(ZERO_PROBABILITY);
        assertEquals(DEFAULT_DATA, textService.parseText(rawText).getDecoded());
    }
}
