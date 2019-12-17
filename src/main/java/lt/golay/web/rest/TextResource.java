package lt.golay.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lt.golay.domain.Text;
import lt.golay.repository.TextRepository;
import lt.golay.service.TextService;
import lt.golay.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link lt.golay.domain.Text}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TextResource {

    private final Logger log = LoggerFactory.getLogger(TextResource.class);

    private static final String ENTITY_NAME = "text";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TextRepository textRepository;

    private final TextService textService;

    public TextResource(TextRepository textRepository, final TextService textService) {
        this.textRepository = textRepository;
        this.textService = textService;
    }

    /**
     * {@code POST  /texts} : Create a new text.
     *
     * @param text the text to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new text, or with status {@code 400 (Bad Request)} if the text has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/texts")
    public ResponseEntity<Text> createText(@RequestBody Text text) throws URISyntaxException {
        log.debug("REST request to save Text : {}", text);
        if (text.getId() != null) {
            throw new BadRequestAlertException("A new text cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Text result = textService.parseText(text);
        return ResponseEntity.created(new URI("/api/texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /texts} : Updates an existing text.
     *
     * @param text the text to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated text,
     * or with status {@code 400 (Bad Request)} if the text is not valid,
     * or with status {@code 500 (Internal Server Error)} if the text couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/texts")
    public ResponseEntity<Text> updateText(@RequestBody Text text) throws URISyntaxException {
        log.debug("REST request to update Text : {}", text);
        if (text.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Text result = textRepository.save(text);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, text.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /texts} : get all the texts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of texts in body.
     */
    @GetMapping("/texts")
    public List<Text> getAllTexts() {
        log.debug("REST request to get all Texts");
        return textRepository.findAll();
    }

    /**
     * {@code GET  /texts/:id} : get the "id" text.
     *
     * @param id the id of the text to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the text, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/texts/{id}")
    public ResponseEntity<Text> getText(@PathVariable Long id) {
        log.debug("REST request to get Text : {}", id);
        Optional<Text> text = textRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(text);
    }

    /**
     * {@code DELETE  /texts/:id} : delete the "id" text.
     *
     * @param id the id of the text to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/texts/{id}")
    public ResponseEntity<Void> deleteText(@PathVariable Long id) {
        log.debug("REST request to delete Text : {}", id);
        textRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
