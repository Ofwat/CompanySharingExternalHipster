package uk.gov.ofwat.external.repository;

import org.springframework.data.repository.query.Param;
import uk.gov.ofwat.external.domain.CompanyDataCollection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CompanyDataCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDataCollectionRepository extends JpaRepository<CompanyDataCollection,Long> {

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.companyOwner.login = ?#{principal.username}")
    List<CompanyDataCollection> findByCompanyOwnerIsCurrentUser();

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.companyReviewer.login = ?#{principal.username}")
    List<CompanyDataCollection> findByCompanyReviewerIsCurrentUser();

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.dataCollection.id = :dcId")
    List<CompanyDataCollection> findByCompanyByDataCollection(@Param("dcId") Long dcId);

 }
