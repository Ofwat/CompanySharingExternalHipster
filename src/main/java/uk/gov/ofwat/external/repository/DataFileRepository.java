package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.DataFile;


/**
 * Spring Data JPA repository for the DataFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataFileRepository extends JpaRepository<DataFile,Long> {

}
