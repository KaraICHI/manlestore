package com.sxu.yusa.repository;

import com.sxu.yusa.domain.OrderItem;
import com.sxu.yusa.domain.Product;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderMasterId(long id);
    List<OrderItem> findByProductId(long id);

 }
