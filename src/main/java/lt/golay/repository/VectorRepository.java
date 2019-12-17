package lt.golay.repository;
import lt.golay.domain.Vector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VectorRepository extends JpaRepository<Vector, Long> {

}
