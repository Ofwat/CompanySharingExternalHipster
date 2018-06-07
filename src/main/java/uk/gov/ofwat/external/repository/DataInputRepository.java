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
public interface DataInputRepository extends JpaRepository<DataInput, Long> {

    @Query("select data_input from DataInput data_input where data_input.owner.login = ?#{principal.username}")
    List<DataInput> findByOwnerIsCurrentUser();

    @Query("select data_input from DataInput data_input where data_input.reviewer.login = ?#{principal.username}")
    List<DataInput> findByReviewerIsCurrentUser();

    @Query("SELECT coalesce(max(di.orderIndex), -1) FROM DataInput di WHERE data_Bundle_Id = :dataBundleId")
    Long getMaxOrderIndex(@Param("dataBundleId") Long dataBundleId);

    @Modifying
    @Query("UPDATE DataInput di set di.orderIndex = :orderIndex WHERE id = :dataInputId")
    void updateOrderIndexForId(@Param("orderIndex") Long orderIndex,
                               @Param("dataInputId") Long dataInputId);

    @Query("select data_input from DataInput data_input where data_input.dataBundle.id = ?#{id}")
    List<DataInput> findByDataBundle(@Param("id") Long id);


    @Query("select case when count(data_input) > 0  then false else true end from DataInput data_input where data_input.id = :id and data_input.status.id = 4")
    Boolean isPublished(@Param("id") Long id);

}
