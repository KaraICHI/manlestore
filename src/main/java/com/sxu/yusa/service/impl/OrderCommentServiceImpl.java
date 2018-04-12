package com.sxu.yusa.service.impl;

import com.sxu.yusa.service.OrderCommentService;
import com.sxu.yusa.domain.OrderComment;
import com.sxu.yusa.repository.OrderCommentRepository;
import com.sxu.yusa.service.dto.OrderCommentDTO;
import com.sxu.yusa.service.mapper.OrderCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OrderComment.
 */
@Service
@Transactional
public class OrderCommentServiceImpl implements OrderCommentService {

    private final Logger log = LoggerFactory.getLogger(OrderCommentServiceImpl.class);

    private final OrderCommentRepository orderCommentRepository;

    private final OrderCommentMapper orderCommentMapper;

    public OrderCommentServiceImpl(OrderCommentRepository orderCommentRepository, OrderCommentMapper orderCommentMapper) {
        this.orderCommentRepository = orderCommentRepository;
        this.orderCommentMapper = orderCommentMapper;
    }

    /**
     * Save a orderComment.
     *
     * @param orderCommentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderCommentDTO save(OrderCommentDTO orderCommentDTO) {
        log.debug("Request to save OrderComment : {}", orderCommentDTO);
        OrderComment orderComment = orderCommentMapper.toEntity(orderCommentDTO);
        orderComment = orderCommentRepository.save(orderComment);
        return orderCommentMapper.toDto(orderComment);
    }

    /**
     * Get all the orderComments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderComments");
        return orderCommentRepository.findAll(pageable)
            .map(orderCommentMapper::toDto);
    }

    /**
     * Get one orderComment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrderCommentDTO findOne(Long id) {
        log.debug("Request to get OrderComment : {}", id);
        OrderComment orderComment = orderCommentRepository.findOne(id);
        return orderCommentMapper.toDto(orderComment);
    }

    /**
     * Delete the orderComment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderComment : {}", id);
        orderCommentRepository.delete(id);
    }
}
