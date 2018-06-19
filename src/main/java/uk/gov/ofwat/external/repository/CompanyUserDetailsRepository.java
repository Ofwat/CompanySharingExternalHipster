package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.CompanyUserDetails;
import uk.gov.ofwat.external.domain.User;

import java.util.List;

@Repository
public interface CompanyUserDetailsRepository extends JpaRepository<CompanyUserDetails,Long> {

    List<CompanyUserDetails> findAllByCompanyAndUser(Company company, User user);

    List<CompanyUserDetails> findAllByCompany(Company company);

}
