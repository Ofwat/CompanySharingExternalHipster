package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.CompanyDataBundle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
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
    
}
