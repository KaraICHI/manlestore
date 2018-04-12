package com.sxu.yusa.repository;

import com.sxu.yusa.domain.ArticalCommentReply;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArticalCommentReply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticalCommentReplyRepository extends JpaRepository<ArticalCommentReply, Long> {

}
