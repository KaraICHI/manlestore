package com.sxu.yusa.repository;

import com.sxu.yusa.domain.Artical;
import org.hibernate.annotations.SQLInsert;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the Artical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticalRepository extends JpaRepository<Artical, Long> {
    List<Artical> findAllByOrderByCreatDateDesc();

    List<Artical> findAllByOrderByFavoriteDesc();
}
