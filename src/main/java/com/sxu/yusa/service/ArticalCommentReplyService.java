package com.sxu.yusa.service;

import com.sxu.yusa.service.dto.ArticalCommentReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ArticalCommentReply.
 */
public interface ArticalCommentReplyService {

    /**
     * Save a articalCommentReply.
     *
     * @param articalCommentReplyDTO the entity to save
     * @return the persisted entity
     */
    ArticalCommentReplyDTO save(ArticalCommentReplyDTO articalCommentReplyDTO);

    /**
     * Get all the articalCommentReplies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ArticalCommentReplyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" articalCommentReply.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ArticalCommentReplyDTO findOne(Long id);

    /**
     * Delete the "id" articalCommentReply.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
