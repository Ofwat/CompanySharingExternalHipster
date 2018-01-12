package uk.gov.ofwat.external.repository;

import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.DataCollection;

import java.util.Optional;


/**
 * Spring Data JPA repository for the DataCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataCollectionRepository extends JpaRepository<DataCollection,Long> {

    DataCollection findOneByName(String name);

    void deleteByName(String name);
}
