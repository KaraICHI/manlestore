package com.sxu.yusa.repository;

import com.sxu.yusa.domain.OrderMaster;
import com.sxu.yusa.domain.enumeration.OrderStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the OrderMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, Long> {

    List<OrderMaster> findByClientUserIdAndOrderStatus(Long id,OrderStatus status);
}
