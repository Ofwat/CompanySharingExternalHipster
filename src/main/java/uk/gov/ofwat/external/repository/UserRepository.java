package uk.gov.ofwat.external.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByLoginNot(Pageable pageable, String login);


    @Query("select company from Company company join company.companyUserDetails cud join cud.authority a join cud.user u where u.id =:userId and a.name ='ROLE_OFWAT_ADMIN'")
    Optional<List<Company>> findAllWhereUserIsAdmin(@Param("userId") Long userId);

    @Query(value="select user from User user join user.companyUserDetails cud join cud.company comp where comp.id =:companyId and user.login <> ?#{principal.username}")
    Page<User> getAllManagedUsersByCompany( @Param("companyId") Long companyId,Pageable pageable);

}
