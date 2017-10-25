package uk.gov.ofwat.external.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import java.util.Optional;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    Optional<RegistrationRequest> findOneByRegistrationKey(String registrationKey);

    Optional<RegistrationRequest> findOneByLogin(String login);

    Page<RegistrationRequest> findAllByUserActivatedIsFalseAndCompanyIs(Pageable pageable, Company company);

}
