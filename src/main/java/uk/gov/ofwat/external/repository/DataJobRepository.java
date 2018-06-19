package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.DataJob;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the DataCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataJobRepository extends JpaRepository<DataJob,Long> {
    List<DataJob> findDistinctDataJobsByJobStatusOrderByIdAsc(String jobStatus);

    Optional<DataJob> findDistinctDataJobByJobStatusOrderByIdAsc(String jobStatus);

    Optional<DataJob> findDistinctDataJobByUuid(String uuid);

    @Modifying
    @Query("UPDATE DataJob dj SET dj.jobStatus = :jobStatus, dj.rejectedReason = :rejectedReason, dj.updated = :updated WHERE dj.uuid = :uuid")
    void updateDataJobForUuid(@Param("uuid") String uuid, @Param("jobStatus") String jobStatus, @Param("rejectedReason") String rejectedReason, @Param("updated") Boolean updated);

    /**
     *
     * @return
     */
    List<DataJob> findByUpdatedOrderByCompanyDataInputId(@Param("updated") boolean updated);
}
