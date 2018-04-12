package com.sxu.yusa.repository;

import com.sxu.yusa.domain.ClientUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ClientUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {
    @Query("select distinct client_user from ClientUser client_user left join fetch client_user.products")
    List<ClientUser> findAllWithEagerRelationships();

    @Query("select client_user from ClientUser client_user left join fetch client_user.products where client_user.id =:id")
    ClientUser findOneWithEagerRelationships(@Param("id") Long id);

    ClientUser findByUserName(@Param("userName") String userName);

    ClientUser findByPhone(@Param("phone") String phone);

}
