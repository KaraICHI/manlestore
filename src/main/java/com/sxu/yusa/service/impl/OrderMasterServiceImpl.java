package com.sxu.yusa.service.impl;

import com.sxu.yusa.domain.enumeration.OrderStatus;
import com.sxu.yusa.service.OrderMasterService;
import com.sxu.yusa.domain.OrderMaster;
import com.sxu.yusa.repository.OrderMasterRepository;
import com.sxu.yusa.service.dto.OrderMasterDTO;
import com.sxu.yusa.service.mapper.OrderMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing OrderMaster.
 */
@Service
@Transactional
public class OrderMasterServiceImpl implements OrderMasterService {

    private final Logger log = LoggerFactory.getLogger(OrderMasterServiceImpl.class);

    private final OrderMasterRepository orderMasterRepository;

    private final OrderMasterMapper orderMasterMapper;

    public OrderMasterServiceImpl(OrderMasterRepository orderMasterRepository, OrderMasterMapper orderMasterMapper) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderMasterMapper = orderMasterMapper;
    }

    /**
     * Save a orderMaster.
     *
     * @param orderMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderMasterDTO save(OrderMasterDTO orderMasterDTO) {
        log.debug("Request to save OrderMaster : {}", orderMasterDTO);
        OrderMaster orderMaster = orderMasterMapper.toEntity(orderMasterDTO);
        orderMaster = orderMasterRepository.save(orderMaster);
        return orderMasterMapper.toDto(orderMaster);
    }

    /**
     * Get all the orderMasters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderMasters");
        return orderMasterRepository.findAll(pageable)
            .map(orderMasterMapper::toDto);
    }

    /**
     * Get one orderMaster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrderMasterDTO findOne(Long id) {
        log.debug("Request to get OrderMaster : {}", id);
        OrderMaster orderMaster = orderMasterRepository.findOne(id);
        return orderMasterMapper.toDto(orderMaster);
    }

    /**
     * Delete the orderMaster by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderMaster : {}", id);
        orderMasterRepository.delete(id);
    }

    @Override
    public List<OrderMasterDTO> findByClientUserId(Long id, OrderStatus status) {
        return orderMasterMapper.toDto(orderMasterRepository.findByClientUserIdAndOrderStatus(id,status));
    }

}
