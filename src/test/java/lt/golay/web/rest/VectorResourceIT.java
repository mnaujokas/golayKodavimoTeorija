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
import lt.golay.domain.Vector;
import lt.golay.repository.VectorRepository;
import lt.golay.service.VectorService;
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
 * Integration tests for the {@link VectorResource} REST controller.
 */
@SpringBootTest(classes = GolayApp.class)
public class VectorResourceIT {

    private static final String DEFAULT_DATA = "000000000000";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_ENCODED = "AAAAAAAAAA";
    private static final String UPDATED_ENCODED = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSFERED = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFERED = "BBBBBBBBBB";

    private static final String DEFAULT_DECODED = "AAAAAAAAAA";
    private static final String UPDATED_DECODED = "BBBBBBBBBB";

    private static final Double DEFAULT_PROBABILITY = 1D;
    private static final Double UPDATED_PROBABILITY = 2D;
    private static final String DEFAULT_ERRORS = "errors";
    private static final String UPDATED_ERRORS = "updatedErrors";

    @Autowired
    private VectorRepository vectorRepository;

    @Autowired
    private VectorService vectorService;

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

    private MockMvc restVectorMockMvc;

    private Vector vector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final VectorResource vectorResource = new VectorResource(vectorRepository, vectorService);
        this.restVectorMockMvc = MockMvcBuilders.standaloneSetup(vectorResource)
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
    public static Vector createEntity(EntityManager em) {
        Vector vector = new Vector()
            .data(DEFAULT_DATA)
            .encoded(DEFAULT_ENCODED)
            .transfered(DEFAULT_TRANSFERED)
            .decoded(DEFAULT_DECODED)
            .probability(DEFAULT_PROBABILITY)
            .errors(DEFAULT_ERRORS);
        return vector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vector createUpdatedEntity(EntityManager em) {
        Vector vector = new Vector()
            .data(UPDATED_DATA)
            .encoded(UPDATED_ENCODED)
            .transfered(UPDATED_TRANSFERED)
            .decoded(UPDATED_DECODED)
            .probability(UPDATED_PROBABILITY)
            .errors(UPDATED_ERRORS);
        return vector;
    }

    @BeforeEach
    public void initTest() {
        vector = createEntity(em);
    }



    @Test
    @Transactional
    public void createVectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vectorRepository.findAll().size();

        // Create the Vector with an existing ID
        vector.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVectorMockMvc.perform(post("/api/vectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vector)))
            .andExpect(status().isBadRequest());

        // Validate the Vector in the database
        List<Vector> vectorList = vectorRepository.findAll();
        assertThat(vectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVectors() throws Exception {
        // Initialize the database
        vectorRepository.saveAndFlush(vector);

        // Get all the vectorList
        restVectorMockMvc.perform(get("/api/vectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vector.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].encoded").value(hasItem(DEFAULT_ENCODED)))
            .andExpect(jsonPath("$.[*].transfered").value(hasItem(DEFAULT_TRANSFERED)))
            .andExpect(jsonPath("$.[*].decoded").value(hasItem(DEFAULT_DECODED)))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].errors").value(hasItem(DEFAULT_ERRORS)));
    }

    @Test
    @Transactional
    public void getVector() throws Exception {
        // Initialize the database
        vectorRepository.saveAndFlush(vector);

        // Get the vector
        restVectorMockMvc.perform(get("/api/vectors/{id}", vector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vector.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.encoded").value(DEFAULT_ENCODED))
            .andExpect(jsonPath("$.transfered").value(DEFAULT_TRANSFERED))
            .andExpect(jsonPath("$.decoded").value(DEFAULT_DECODED))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY.doubleValue()))
            .andExpect(jsonPath("$.errors").value(DEFAULT_ERRORS));
    }

    @Test
    @Transactional
    public void getNonExistingVector() throws Exception {
        // Get the vector
        restVectorMockMvc.perform(get("/api/vectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }



    @Test
    @Transactional
    public void updateNonExistingVector() throws Exception {
        int databaseSizeBeforeUpdate = vectorRepository.findAll().size();

        // Create the Vector

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVectorMockMvc.perform(put("/api/vectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vector)))
            .andExpect(status().isBadRequest());

        // Validate the Vector in the database
        List<Vector> vectorList = vectorRepository.findAll();
        assertThat(vectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVector() throws Exception {
        // Initialize the database
        vectorRepository.saveAndFlush(vector);

        int databaseSizeBeforeDelete = vectorRepository.findAll().size();

        // Delete the vector
        restVectorMockMvc.perform(delete("/api/vectors/{id}", vector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vector> vectorList = vectorRepository.findAll();
        assertThat(vectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
