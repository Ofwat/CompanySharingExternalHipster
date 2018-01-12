package uk.gov.ofwat.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.EncryptedEntity;

@Repository
public interface EncryptedEntityRepository extends JpaRepository<EncryptedEntity, Long> {

}
