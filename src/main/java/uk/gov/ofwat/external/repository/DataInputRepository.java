package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.DataInput;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the DataInput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataInputRepository extends JpaRepository<DataInput,Long> {

    @Query("select data_input from DataInput data_input where data_input.owner.login = ?#{principal.username}")
    List<DataInput> findByOwnerIsCurrentUser();

    @Query("select data_input from DataInput data_input where data_input.reviewer.login = ?#{principal.username}")
    List<DataInput> findByReviewerIsCurrentUser();
    
}
