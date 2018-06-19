package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.PublishingStatus;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PublishingStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublishingStatusRepository extends JpaRepository<PublishingStatus,Long> {
    Optional<PublishingStatus> findOneByStatus(String status);
}
