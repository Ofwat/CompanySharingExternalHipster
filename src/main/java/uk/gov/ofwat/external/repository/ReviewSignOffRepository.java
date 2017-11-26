package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.ReviewSignOff;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ReviewSignOff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewSignOffRepository extends JpaRepository<ReviewSignOff,Long> {

    @Query("select review_sign_off from ReviewSignOff review_sign_off where review_sign_off.signatory.login = ?#{principal.username}")
    List<ReviewSignOff> findBySignatoryIsCurrentUser();
    
}
