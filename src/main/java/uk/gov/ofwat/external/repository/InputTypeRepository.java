package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.InputType;


/**
 * Spring Data JPA repository for the InputType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputTypeRepository extends JpaRepository<InputType,Long> {

}
