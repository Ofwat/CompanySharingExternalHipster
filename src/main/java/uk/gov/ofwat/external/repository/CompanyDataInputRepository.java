package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.CompanyDataInput;

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

    @Query("select company_data_input from CompanyDataInput company_data_input left join fetch  company_data_input.companyDataBundle companyDataBundle where companyDataBundle.id = :id")
    List<CompanyDataInput> findByCompanyDataBundle(@Param("id") Long id);

    @Query("SELECT coalesce(max(company_data_input.orderIndex), -1) FROM CompanyDataInput company_data_input WHERE company_data_Bundle_Id = :companyDataBundleId")
    Long getMaxOrderIndex(@Param("companyDataBundleId") Long companyDataBundleId);


}
