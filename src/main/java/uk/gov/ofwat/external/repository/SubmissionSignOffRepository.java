package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.SubmissionSignOff;

import java.util.List;

/**
 * Spring Data JPA repository for the SubmissionSignOff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubmissionSignOffRepository extends JpaRepository<SubmissionSignOff,Long> {

    @Query("select submission_sign_off from SubmissionSignOff submission_sign_off where submission_sign_off.signatory.login = ?#{principal.username}")
    List<SubmissionSignOff> findBySignatoryIsCurrentUser();

}
