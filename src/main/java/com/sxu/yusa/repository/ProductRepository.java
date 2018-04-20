package com.sxu.yusa.repository;

import com.sxu.yusa.domain.Product;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByOrderBySellDesc();

    List<Product> findAllByOrderByProduceDateDesc();

    List<Product> findAllByOrderByOriginPriceDesc();


}
