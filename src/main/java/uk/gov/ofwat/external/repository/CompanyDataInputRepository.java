package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.CompanyDataInput;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CompanyDataInput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDataInputRepository extends JpaRepository<CompanyDataInput,Long> {

    @Query("select company_data_input from CompanyDataInput company_data_input where company_data_input.companyOwner.login = ?#{principal.username}")
    List<CompanyDataInput> findByCompanyOwnerIsCurrentUser();

    @Query("select company_data_input from CompanyDataInput company_data_input where company_data_input.companyReviewer.login = ?#{principal.username}")
    List<CompanyDataInput> findByCompanyReviewerIsCurrentUser();
    
}
