package uk.gov.ofwat.external.repository;

import org.springframework.data.repository.query.Param;
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

    @Query("SELECT coalesce(max(db.orderIndex), -1) FROM DataInput db WHERE data_Bundle_Id = :dataBundleId")
    Long getMaxOrderIndex(@Param("dataBundleId") Long dataBundleId);

    @Modifying
    @Query("UPDATE DataInput db set db.orderIndex = :orderIndex WHERE data_Bundle_Id = :dataBundleId")
    void updateOrderIndexForId(@Param("orderIndex") Long orderIndex,
                               @Param("dataBundleId") Long dataBundleId);

}
