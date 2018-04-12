package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.RejectionCodes;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface  RejectionRepository extends JpaRepository<RejectionCodes, Long> {
    Optional<RejectionCodes> findOneByDescription(String description);
}
