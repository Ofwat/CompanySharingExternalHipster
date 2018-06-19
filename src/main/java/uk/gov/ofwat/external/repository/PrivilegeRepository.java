package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.ofwat.external.domain.Privilege;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, String> {
}
