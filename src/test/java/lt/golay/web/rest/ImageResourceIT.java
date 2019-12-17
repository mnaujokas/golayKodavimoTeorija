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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;
import lt.golay.GolayApp;
import lt.golay.domain.Image;
import lt.golay.repository.ImageRepository;
import lt.golay.service.ImageService;
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
 * Integration tests for the {@link ImageResource} REST controller.
 */
@SpringBootTest(classes = GolayApp.class)
public class ImageResourceIT {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_NO_ENCODING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NO_ENCODING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NO_ENCODING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NO_ENCODING_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_WITH_ENCODING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_WITH_ENCODING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_WITH_ENCODING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_WITH_ENCODING_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_PROBABILITY = 1D;
    private static final Double UPDATED_PROBABILITY = 2D;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

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

    private MockMvc restImageMockMvc;

    private Image image;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageResource imageResource = new ImageResource(imageRepository, imageService);
        this.restImageMockMvc = MockMvcBuilders.standaloneSetup(imageResource)
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
    public static Image createEntity(EntityManager em) {
        Image image = new Image()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .noEncoding(DEFAULT_NO_ENCODING)
            .noEncodingContentType(DEFAULT_NO_ENCODING_CONTENT_TYPE)
            .withEncoding(DEFAULT_WITH_ENCODING)
            .withEncodingContentType(DEFAULT_WITH_ENCODING_CONTENT_TYPE)
            .probability(DEFAULT_PROBABILITY);
        return image;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createUpdatedEntity(EntityManager em) {
        Image image = new Image()
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .noEncoding(UPDATED_NO_ENCODING)
            .noEncodingContentType(UPDATED_NO_ENCODING_CONTENT_TYPE)
            .withEncoding(UPDATED_WITH_ENCODING)
            .withEncodingContentType(UPDATED_WITH_ENCODING_CONTENT_TYPE)
            .probability(UPDATED_PROBABILITY);
        return image;
    }

    @BeforeEach
    public void initTest() {
        image = createEntity(em);
    }



    @Test
    @Transactional
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image with an existing ID
        image.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(image)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].noEncodingContentType").value(hasItem(DEFAULT_NO_ENCODING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].noEncoding").value(hasItem(Base64Utils.encodeToString(DEFAULT_NO_ENCODING))))
            .andExpect(jsonPath("$.[*].withEncodingContentType").value(hasItem(DEFAULT_WITH_ENCODING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].withEncoding").value(hasItem(Base64Utils.encodeToString(DEFAULT_WITH_ENCODING))))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.noEncodingContentType").value(DEFAULT_NO_ENCODING_CONTENT_TYPE))
            .andExpect(jsonPath("$.noEncoding").value(Base64Utils.encodeToString(DEFAULT_NO_ENCODING)))
            .andExpect(jsonPath("$.withEncodingContentType").value(DEFAULT_WITH_ENCODING_CONTENT_TYPE))
            .andExpect(jsonPath("$.withEncoding").value(Base64Utils.encodeToString(DEFAULT_WITH_ENCODING)))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findById(image.getId()).get();
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(updatedImage);
        updatedImage
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .noEncoding(UPDATED_NO_ENCODING)
            .noEncodingContentType(UPDATED_NO_ENCODING_CONTENT_TYPE)
            .withEncoding(UPDATED_WITH_ENCODING)
            .withEncodingContentType(UPDATED_WITH_ENCODING_CONTENT_TYPE)
            .probability(UPDATED_PROBABILITY);

        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImage)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testImage.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testImage.getNoEncoding()).isEqualTo(UPDATED_NO_ENCODING);
        assertThat(testImage.getNoEncodingContentType()).isEqualTo(UPDATED_NO_ENCODING_CONTENT_TYPE);
        assertThat(testImage.getWithEncoding()).isEqualTo(UPDATED_WITH_ENCODING);
        assertThat(testImage.getWithEncodingContentType()).isEqualTo(UPDATED_WITH_ENCODING_CONTENT_TYPE);
        assertThat(testImage.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Create the Image

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(image)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Delete the image
        restImageMockMvc.perform(delete("/api/images/{id}", image.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
