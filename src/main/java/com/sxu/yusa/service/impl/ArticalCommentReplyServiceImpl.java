package com.sxu.yusa.service.impl;

import com.sxu.yusa.service.ArticalCommentReplyService;
import com.sxu.yusa.domain.ArticalCommentReply;
import com.sxu.yusa.repository.ArticalCommentReplyRepository;
import com.sxu.yusa.service.dto.ArticalCommentReplyDTO;
import com.sxu.yusa.service.mapper.ArticalCommentReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ArticalCommentReply.
 */
@Service
@Transactional
public class ArticalCommentReplyServiceImpl implements ArticalCommentReplyService {

    private final Logger log = LoggerFactory.getLogger(ArticalCommentReplyServiceImpl.class);

    private final ArticalCommentReplyRepository articalCommentReplyRepository;

    private final ArticalCommentReplyMapper articalCommentReplyMapper;

    public ArticalCommentReplyServiceImpl(ArticalCommentReplyRepository articalCommentReplyRepository, ArticalCommentReplyMapper articalCommentReplyMapper) {
        this.articalCommentReplyRepository = articalCommentReplyRepository;
        this.articalCommentReplyMapper = articalCommentReplyMapper;
    }

    /**
     * Save a articalCommentReply.
     *
     * @param articalCommentReplyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ArticalCommentReplyDTO save(ArticalCommentReplyDTO articalCommentReplyDTO) {
        log.debug("Request to save ArticalCommentReply : {}", articalCommentReplyDTO);
        ArticalCommentReply articalCommentReply = articalCommentReplyMapper.toEntity(articalCommentReplyDTO);
        articalCommentReply = articalCommentReplyRepository.save(articalCommentReply);
        return articalCommentReplyMapper.toDto(articalCommentReply);
    }

    /**
     * Get all the articalCommentReplies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticalCommentReplyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticalCommentReplies");
        return articalCommentReplyRepository.findAll(pageable)
            .map(articalCommentReplyMapper::toDto);
    }

    /**
     * Get one articalCommentReply by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArticalCommentReplyDTO findOne(Long id) {
        log.debug("Request to get ArticalCommentReply : {}", id);
        ArticalCommentReply articalCommentReply = articalCommentReplyRepository.findOne(id);
        return articalCommentReplyMapper.toDto(articalCommentReply);
    }

    /**
     * Delete the articalCommentReply by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticalCommentReply : {}", id);
        articalCommentReplyRepository.delete(id);
    }
}
