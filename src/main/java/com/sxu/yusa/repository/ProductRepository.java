package com.sxu.yusa.repository;

import com.sxu.yusa.domain.Product;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
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

    List<Product> findAllByBrand(String brand);

    List<Product> findAllByCategoryId(Long id);

    List<Product> findAllByThemeId(Long id);

    List<Product> findAllBySupply(String supply);

    List<Product> findAllByCoverPriceBetween(BigDecimal minPrice,BigDecimal maxPrice);
}
