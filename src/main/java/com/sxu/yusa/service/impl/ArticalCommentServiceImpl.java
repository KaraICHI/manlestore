package com.sxu.yusa.service.impl;

import com.sxu.yusa.service.ArticalCommentService;
import com.sxu.yusa.domain.ArticalComment;
import com.sxu.yusa.repository.ArticalCommentRepository;
import com.sxu.yusa.service.dto.ArticalCommentDTO;
import com.sxu.yusa.service.mapper.ArticalCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ArticalComment.
 */
@Service
@Transactional
public class ArticalCommentServiceImpl implements ArticalCommentService {

    private final Logger log = LoggerFactory.getLogger(ArticalCommentServiceImpl.class);

    private final ArticalCommentRepository articalCommentRepository;

    private final ArticalCommentMapper articalCommentMapper;

    public ArticalCommentServiceImpl(ArticalCommentRepository articalCommentRepository, ArticalCommentMapper articalCommentMapper) {
        this.articalCommentRepository = articalCommentRepository;
        this.articalCommentMapper = articalCommentMapper;
    }

    /**
     * Save a articalComment.
     *
     * @param articalCommentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ArticalCommentDTO save(ArticalCommentDTO articalCommentDTO) {
        log.debug("Request to save ArticalComment : {}", articalCommentDTO);
        ArticalComment articalComment = articalCommentMapper.toEntity(articalCommentDTO);
        articalComment = articalCommentRepository.save(articalComment);
        return articalCommentMapper.toDto(articalComment);
    }

    /**
     * Get all the articalComments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticalCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticalComments");
        return articalCommentRepository.findAll(pageable)
            .map(articalCommentMapper::toDto);
    }

    /**
     * Get one articalComment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArticalCommentDTO findOne(Long id) {
        log.debug("Request to get ArticalComment : {}", id);
        ArticalComment articalComment = articalCommentRepository.findOne(id);
        return articalCommentMapper.toDto(articalComment);
    }

    /**
     * Delete the articalComment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticalComment : {}", id);
        articalCommentRepository.delete(id);
    }
}
