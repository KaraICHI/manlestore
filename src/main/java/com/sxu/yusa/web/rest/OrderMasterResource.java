package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.service.OrderMasterService;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.OrderMasterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderMaster.
 */
@RestController
@RequestMapping("/api")
public class OrderMasterResource {

    private final Logger log = LoggerFactory.getLogger(OrderMasterResource.class);

    private static final String ENTITY_NAME = "orderMaster";

    private final OrderMasterService orderMasterService;

    public OrderMasterResource(OrderMasterService orderMasterService) {
        this.orderMasterService = orderMasterService;
    }

    /**
     * POST  /order-masters : Create a new orderMaster.
     *
     * @param orderMasterDTO the orderMasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderMasterDTO, or with status 400 (Bad Request) if the orderMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-masters")
    @Timed
    public ResponseEntity<OrderMasterDTO> createOrderMaster(@RequestBody OrderMasterDTO orderMasterDTO) throws URISyntaxException {
        log.debug("REST request to save OrderMaster : {}", orderMasterDTO);
        if (orderMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderMasterDTO result = orderMasterService.save(orderMasterDTO);
        return ResponseEntity.created(new URI("/api/order-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-masters : Updates an existing orderMaster.
     *
     * @param orderMasterDTO the orderMasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderMasterDTO,
     * or with status 400 (Bad Request) if the orderMasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderMasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-masters")
    @Timed
    public ResponseEntity<OrderMasterDTO> updateOrderMaster(@RequestBody OrderMasterDTO orderMasterDTO) throws URISyntaxException {
        log.debug("REST request to update OrderMaster : {}", orderMasterDTO);
        if (orderMasterDTO.getId() == null) {
            return createOrderMaster(orderMasterDTO);
        }
        OrderMasterDTO result = orderMasterService.save(orderMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-masters : get all the orderMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderMasters in body
     */
    @GetMapping("/order-masters")
    @Timed
    public ResponseEntity<List<OrderMasterDTO>> getAllOrderMasters(Pageable pageable) {
        log.debug("REST request to get a page of OrderMasters");
        Page<OrderMasterDTO> page = orderMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-masters/:id : get the "id" orderMaster.
     *
     * @param id the id of the orderMasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderMasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-masters/{id}")
    @Timed
    public ResponseEntity<OrderMasterDTO> getOrderMaster(@PathVariable Long id) {
        log.debug("REST request to get OrderMaster : {}", id);
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderMasterDTO));
    }

    /**
     * DELETE  /order-masters/:id : delete the "id" orderMaster.
     *
     * @param id the id of the orderMasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderMaster(@PathVariable Long id) {
        log.debug("REST request to delete OrderMaster : {}", id);
        orderMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
