package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;

import java.util.Optional;


/**
 * Spring Data JPA repository for the NotifyMessageTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotifyMessageTemplateRepository extends JpaRepository<NotifyMessageTemplate,Long> {
    Optional<NotifyMessageTemplate> findOneByName(String name);
}
