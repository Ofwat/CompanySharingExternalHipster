package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.InputType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InputType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputTypeRepository extends JpaRepository<InputType,Long> {
    
}
