package com.sxu.yusa.service;

import com.sxu.yusa.service.dto.ArticalCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ArticalComment.
 */
public interface ArticalCommentService {

    /**
     * Save a articalComment.
     *
     * @param articalCommentDTO the entity to save
     * @return the persisted entity
     */
    ArticalCommentDTO save(ArticalCommentDTO articalCommentDTO);

    /**
     * Get all the articalComments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ArticalCommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" articalComment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ArticalCommentDTO findOne(Long id);

    /**
     * Delete the "id" articalComment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
