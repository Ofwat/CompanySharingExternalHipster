package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.DataCollection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DataCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataCollectionRepository extends JpaRepository<DataCollection,Long> {
    
}
