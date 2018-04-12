package com.sxu.yusa.repository;

import com.sxu.yusa.domain.OrderComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrderComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderCommentRepository extends JpaRepository<OrderComment, Long> {

}
