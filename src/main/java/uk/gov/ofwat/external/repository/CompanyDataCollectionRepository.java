package uk.gov.ofwat.external.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.CompanyDataCollection;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyDataCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDataCollectionRepository extends PagingAndSortingRepository<CompanyDataCollection, Long> {

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.companyOwner.login = ?#{principal.username}")
    List<CompanyDataCollection> findByCompanyOwnerIsCurrentUser();

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.companyReviewer.login = ?#{principal.username}")
    List<CompanyDataCollection> findByCompanyReviewerIsCurrentUser();

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.dataCollection.id = :dcId")
    List<CompanyDataCollection> findByCompanyByDataCollection(@Param("dcId") Long dcId);

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.company.id IN ?1")
    Page<CompanyDataCollection> findEligibleDataCollection(List<Long> companyIds, Pageable pageable);

    @Query("select company_data_collection from CompanyDataCollection company_data_collection where company_data_collection.dataCollection.id = :dataCollectionId and company_data_collection.company.id = :companyId")
    CompanyDataCollection findByCompanyByDataCollectionAndCompany(@Param("dataCollectionId") Long dataCollectionId, @Param("companyId") Long companyId);

}
