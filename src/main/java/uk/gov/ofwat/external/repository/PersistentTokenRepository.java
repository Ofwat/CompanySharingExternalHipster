package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.ofwat.external.domain.PersistentToken;
import uk.gov.ofwat.external.domain.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
