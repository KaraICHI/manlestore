package com.sxu.yusa.service;

import com.sxu.yusa.domain.enumeration.OrderStatus;
import com.sxu.yusa.service.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing OrderMaster.
 */
public interface OrderMasterService {

    /**
     * Save a orderMaster.
     *
     * @param orderMasterDTO the entity to save
     * @return the persisted entity
     */
    OrderMasterDTO save(OrderMasterDTO orderMasterDTO);

    /**
     * Get all the orderMasters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderMasterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orderMaster.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrderMasterDTO findOne(Long id);

    /**
     * Delete the "id" orderMaster.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<OrderMasterDTO> findByClientUserId(Long id,OrderStatus status);
}
