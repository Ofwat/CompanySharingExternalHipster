package uk.gov.ofwat.external.repository;

import uk.gov.ofwat.external.domain.Company;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

/*    @Query("select company from Company company left join fetch company.companyUserDetails where company.id =:id")
    Company findOne(@Param("id") Long id);*/

    @Query("select distinct company from Company company left join fetch company.companyUserDetails")
    List<Company> findAllWithEagerRelationships();

    @Query("select company from Company company left join fetch company.companyUserDetails where company.id =:id")
    Company findOneWithEagerRelationships(@Param("id") Long id);

    //@Query("select company from Company company join company.users user join user.authorities a where user.id =:userId and a.name ='ROLE_ADMIN'")
    @Query("select company from Company company join company.companyUserDetails cud join cud.authority a join cud.user u where u.id =:userId and a.name ='ROLE_ADMIN'")
    Optional<List<Company>> findAllWhereUserIsAdmin(@Param("userId") Long userId);

}
