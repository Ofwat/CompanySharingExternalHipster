package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.ReviewSignOff;

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
