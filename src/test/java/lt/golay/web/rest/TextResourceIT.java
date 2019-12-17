package lt.golay.web.rest;

import javax.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import lt.golay.GolayApp;
import lt.golay.domain.Text;
import lt.golay.repository.TextRepository;
import lt.golay.service.TextService;
import lt.golay.web.rest.errors.ExceptionTranslator;
import static lt.golay.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link TextResource} REST controller.
 */
@SpringBootTest(classes = GolayApp.class)
public class TextResourceIT {

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_ENCODED = "AAAAAAAAAA";
    private static final String UPDATED_ENCODED = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSFERED = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFERED = "BBBBBBBBBB";

    private static final String DEFAULT_DECODED = "AAAAAAAAAA";
    private static final String UPDATED_DECODED = "BBBBBBBBBB";

    private static final Double DEFAULT_PROBABILITY = 1D;
    private static final Double UPDATED_PROBABILITY = 2D;

    private static final String DEFAULT_NO_ENCODING = "AAAAAAAAAA";
    private static final String UPDATED_NO_ENCODING = "BBBBBBBBBB";

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TextService textService;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTextMockMvc;

    private Text text;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TextResource textResource = new TextResource(textRepository, textService);
        this.restTextMockMvc = MockMvcBuilders.standaloneSetup(textResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Text createEntity(EntityManager em) {
        Text text = new Text()
            .data(DEFAULT_DATA)
            .decoded(DEFAULT_DECODED)
            .probability(DEFAULT_PROBABILITY)
            .noEncoding(DEFAULT_NO_ENCODING);
        return text;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Text createUpdatedEntity(EntityManager em) {
        Text text = new Text()
            .data(UPDATED_DATA)
            .decoded(UPDATED_DECODED)
            .probability(UPDATED_PROBABILITY)
            .noEncoding(UPDATED_NO_ENCODING);
        return text;
    }

    @BeforeEach
    public void initTest() {
        text = createEntity(em);
    }



    @Test
    @Transactional
    public void createTextWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = textRepository.findAll().size();

        // Create the Text with an existing ID
        text.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextMockMvc.perform(post("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTexts() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList
        restTextMockMvc.perform(get("/api/texts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(text.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].decoded").value(hasItem(DEFAULT_DECODED)))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].noEncoding").value(hasItem(DEFAULT_NO_ENCODING)));
    }

    @Test
    @Transactional
    public void getText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get the text
        restTextMockMvc.perform(get("/api/texts/{id}", text.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(text.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.decoded").value(DEFAULT_DECODED))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY.doubleValue()))
            .andExpect(jsonPath("$.noEncoding").value(DEFAULT_NO_ENCODING));
    }

    @Test
    @Transactional
    public void getNonExistingText() throws Exception {
        // Get the text
        restTextMockMvc.perform(get("/api/texts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text
        Text updatedText = textRepository.findById(text.getId()).get();
        // Disconnect from session so that the updates on updatedText are not directly saved in db
        em.detach(updatedText);
        updatedText
            .data(UPDATED_DATA)
            .decoded(UPDATED_DECODED)
            .probability(UPDATED_PROBABILITY)
            .noEncoding(UPDATED_NO_ENCODING);

        restTextMockMvc.perform(put("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedText)))
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testText.getDecoded()).isEqualTo(UPDATED_DECODED);
        assertThat(testText.getProbability()).isEqualTo(UPDATED_PROBABILITY);
        assertThat(testText.getNoEncoding()).isEqualTo(UPDATED_NO_ENCODING);
    }

    @Test
    @Transactional
    public void updateNonExistingText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Create the Text

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextMockMvc.perform(put("/api/texts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        int databaseSizeBeforeDelete = textRepository.findAll().size();

        // Delete the text
        restTextMockMvc.perform(delete("/api/texts/{id}", text.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
