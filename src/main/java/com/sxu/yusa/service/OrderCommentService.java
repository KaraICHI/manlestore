package com.sxu.yusa.service;

import com.sxu.yusa.service.dto.OrderCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing OrderComment.
 */
public interface OrderCommentService {

    /**
     * Save a orderComment.
     *
     * @param orderCommentDTO the entity to save
     * @return the persisted entity
     */
    OrderCommentDTO save(OrderCommentDTO orderCommentDTO);

    /**
     * Get all the orderComments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderCommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orderComment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrderCommentDTO findOne(Long id);

    /**
     * Delete the "id" orderComment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
