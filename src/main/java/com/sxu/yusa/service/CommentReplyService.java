package com.sxu.yusa.service;

import com.sxu.yusa.service.dto.CommentReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CommentReply.
 */
public interface CommentReplyService {

    /**
     * Save a commentReply.
     *
     * @param commentReplyDTO the entity to save
     * @return the persisted entity
     */
    CommentReplyDTO save(CommentReplyDTO commentReplyDTO);

    /**
     * Get all the commentReplies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CommentReplyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" commentReply.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CommentReplyDTO findOne(Long id);

    /**
     * Delete the "id" commentReply.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
