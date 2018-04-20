package com.sxu.yusa.repository;

import com.sxu.yusa.domain.Artical;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Artical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticalRepository extends JpaRepository<Artical, Long> {


}
