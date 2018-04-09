package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.CompanyStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the CompanyStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyStatusRepository extends JpaRepository<CompanyStatus,Long> {

    Optional<CompanyStatus> findOneByStatus(String status);
}



