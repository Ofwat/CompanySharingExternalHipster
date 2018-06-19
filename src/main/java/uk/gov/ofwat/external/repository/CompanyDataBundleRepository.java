package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.CompanyDataBundle;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyDataBundle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDataBundleRepository extends JpaRepository<CompanyDataBundle,Long> {

    @Query("select company_data_bundle from CompanyDataBundle company_data_bundle where company_data_bundle.companyOwner.login = ?#{principal.username}")
    List<CompanyDataBundle> findByCompanyOwnerIsCurrentUser();

    @Query("select company_data_bundle from CompanyDataBundle company_data_bundle where company_data_bundle.companyReviewer.login = ?#{principal.username}")
    List<CompanyDataBundle> findByCompanyReviewerIsCurrentUser();

    @Query("select company_data_bundle from CompanyDataBundle company_data_bundle where company_data_bundle.dataBundle.id = :bundleId")
    List<CompanyDataBundle> findByCompanyAndBundle(@Param("bundleId") Long bundleId);

    @Query("SELECT coalesce(max(company_data_bundle.orderIndex), -1) FROM CompanyDataBundle company_data_bundle WHERE company_data_Collection_Id = :companyDataBundleId")
    Long getMaxOrderIndex(@Param("companyDataBundleId") Long companyDataBundleId);

    @Query("select coalesce(max(company_data_bundle.orderIndex), -1)  from CompanyDataBundle company_data_bundle where company_data_bundle.companyDataCollection.id = :dataCollectionId and company_data_bundle.company.id = :companyId")
    Long findByCompanyByDataCollectionAndCompany(@Param("dataCollectionId") Long dataCollectionId, @Param("companyId") Long companyId);


}
