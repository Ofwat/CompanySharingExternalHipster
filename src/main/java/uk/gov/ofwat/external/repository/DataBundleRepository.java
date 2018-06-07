package uk.gov.ofwat.external.repository;

import org.springframework.data.repository.query.Param;
import uk.gov.ofwat.external.domain.DataBundle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DataBundle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataBundleRepository extends JpaRepository<DataBundle, Long> {
    @Query("SELECT coalesce(max(db.orderIndex), -1) FROM DataBundle db WHERE data_Collection_Id = :dataCollectionId")
    Long getMaxOrderIndex(@Param("dataCollectionId") Long dataCollectionId);

    @Modifying
    @Query("UPDATE DataBundle db set db.orderIndex = :orderIndex WHERE id = :dataBundleId")
    void updateOrderIndexForId(@Param("orderIndex") Long orderIndex,
                               @Param("dataBundleId") Long dataBundleId);

    @Query("select case when count(db) > 0  then false else true end from DataBundle db where db.id = :id and db.status.id = 4")
    Boolean isPublished(@Param("id") Long id);
}
