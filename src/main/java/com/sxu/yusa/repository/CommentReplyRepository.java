package com.sxu.yusa.repository;

import com.sxu.yusa.domain.CommentReply;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommentReply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {

}
