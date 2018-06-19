package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.CompanyStatus;

import java.util.Optional;


/**
 * Spring Data JPA repository for the CompanyStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyStatusRepository extends JpaRepository<CompanyStatus,Long> {

    Optional<CompanyStatus> findOneByStatus(String status);
}



