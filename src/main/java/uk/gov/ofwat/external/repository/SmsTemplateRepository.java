package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.SmsTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the SmsTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsTemplateRepository extends JpaRepository<SmsTemplate,Long> {
    Optional<SmsTemplate> findOneByName(String name);
}
