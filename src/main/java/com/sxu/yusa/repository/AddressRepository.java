package com.sxu.yusa.repository;

import com.sxu.yusa.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findByClientUserId(Pageable pageable, Long id);
    List<Address> findByClientUserId( Long id);
}
