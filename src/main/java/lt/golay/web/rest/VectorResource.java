package lt.golay.web.rest;

import javax.validation.Valid;
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
import lt.golay.domain.Vector;
import lt.golay.repository.VectorRepository;
import lt.golay.service.VectorService;
import lt.golay.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link lt.golay.domain.Vector}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VectorResource {

    private final Logger log = LoggerFactory.getLogger(VectorResource.class);

    private static final String ENTITY_NAME = "vector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VectorRepository vectorRepository;
    private final VectorService vectorService;

    public VectorResource(final VectorRepository vectorRepository, final VectorService vectorService) {
        this.vectorRepository = vectorRepository;
        this.vectorService = vectorService;
    }

    /**
     * {@code POST  /vectors} : Create a new vector.
     *
     * @param vector the vector to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vector, or with status {@code 400 (Bad Request)} if the vector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vectors")
    public ResponseEntity<Vector> createVector(@Valid @RequestBody Vector vector) throws URISyntaxException {
        log.debug("REST request to save Vector : {}", vector);
        if (vector.getId() != null) {
            throw new BadRequestAlertException("A new vector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vector result = vectorService.parseVector(vector);
        return ResponseEntity.created(new URI("/api/vectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vectors} : Updates an existing vector.
     *
     * @param vector the vector to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vector,
     * or with status {@code 400 (Bad Request)} if the vector is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vectors")
    public ResponseEntity<Vector> updateVector(@RequestBody Vector vector) throws URISyntaxException {
        log.debug("REST request to update Vector : {}", vector);
        if (vector.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vector result = vectorService.updateVector(vector);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vector.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vectors} : get all the vectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vectors in body.
     */
    @GetMapping("/vectors")
    public List<Vector> getAllVectors() {
        log.debug("REST request to get all Vectors");
        return vectorRepository.findAll();
    }

    /**
     * {@code GET  /vectors/:id} : get the "id" vector.
     *
     * @param id the id of the vector to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vector, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vectors/{id}")
    public ResponseEntity<Vector> getVector(@PathVariable Long id) {
        log.debug("REST request to get Vector : {}", id);
        Optional<Vector> vector = vectorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vector);
    }

    /**
     * {@code DELETE  /vectors/:id} : delete the "id" vector.
     *
     * @param id the id of the vector to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vectors/{id}")
    public ResponseEntity<Void> deleteVector(@PathVariable Long id) {
        log.debug("REST request to delete Vector : {}", id);
        vectorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
