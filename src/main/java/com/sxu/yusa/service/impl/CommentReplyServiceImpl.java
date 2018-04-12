package com.sxu.yusa.service.impl;

import com.sxu.yusa.service.CommentReplyService;
import com.sxu.yusa.domain.CommentReply;
import com.sxu.yusa.repository.CommentReplyRepository;
import com.sxu.yusa.service.dto.CommentReplyDTO;
import com.sxu.yusa.service.mapper.CommentReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CommentReply.
 */
@Service
@Transactional
public class CommentReplyServiceImpl implements CommentReplyService {

    private final Logger log = LoggerFactory.getLogger(CommentReplyServiceImpl.class);

    private final CommentReplyRepository commentReplyRepository;

    private final CommentReplyMapper commentReplyMapper;

    public CommentReplyServiceImpl(CommentReplyRepository commentReplyRepository, CommentReplyMapper commentReplyMapper) {
        this.commentReplyRepository = commentReplyRepository;
        this.commentReplyMapper = commentReplyMapper;
    }

    /**
     * Save a commentReply.
     *
     * @param commentReplyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommentReplyDTO save(CommentReplyDTO commentReplyDTO) {
        log.debug("Request to save CommentReply : {}", commentReplyDTO);
        CommentReply commentReply = commentReplyMapper.toEntity(commentReplyDTO);
        commentReply = commentReplyRepository.save(commentReply);
        return commentReplyMapper.toDto(commentReply);
    }

    /**
     * Get all the commentReplies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommentReplyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommentReplies");
        return commentReplyRepository.findAll(pageable)
            .map(commentReplyMapper::toDto);
    }

    /**
     * Get one commentReply by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommentReplyDTO findOne(Long id) {
        log.debug("Request to get CommentReply : {}", id);
        CommentReply commentReply = commentReplyRepository.findOne(id);
        return commentReplyMapper.toDto(commentReply);
    }

    /**
     * Delete the commentReply by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentReply : {}", id);
        commentReplyRepository.delete(id);
    }
}
