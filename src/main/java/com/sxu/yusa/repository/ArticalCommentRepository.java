package com.sxu.yusa.repository;

import com.sxu.yusa.domain.ArticalComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArticalComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticalCommentRepository extends JpaRepository<ArticalComment, Long> {

}
