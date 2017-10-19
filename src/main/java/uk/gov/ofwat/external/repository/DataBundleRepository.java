package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.DataBundle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DataBundle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataBundleRepository extends JpaRepository<DataBundle,Long> {
    
}
