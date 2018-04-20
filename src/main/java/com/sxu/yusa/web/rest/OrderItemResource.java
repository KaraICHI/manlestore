package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.domain.enumeration.OrderStatus;
import com.sxu.yusa.service.OrderItemService;
import com.sxu.yusa.service.dto.OrderMasterDTO;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.OrderItemDTO;
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
 * REST controller for managing OrderItem.
 */
@RestController
@RequestMapping("/api")
public class OrderItemResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemResource.class);

    private static final String ENTITY_NAME = "orderItem";

    private final OrderItemService orderItemService;

    public OrderItemResource(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * POST  /order-items : Create a new orderItem.
     *
     * @param orderItemDTO the orderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderItemDTO, or with status 400 (Bad Request) if the orderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-items")
    @Timed
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to save OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-items : Updates an existing orderItem.
     *
     * @param orderItemDTO the orderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderItemDTO,
     * or with status 400 (Bad Request) if the orderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-items")
    @Timed
    public ResponseEntity<OrderItemDTO> updateOrderItem(@RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to update OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() == null) {
            return createOrderItem(orderItemDTO);
        }
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-items : get all the orderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderItems in body
     */
    @GetMapping("/order-items")
    @Timed
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems(Pageable pageable) {
        log.debug("REST request to get a page of OrderItems");
        Page<OrderItemDTO> page = orderItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-items/:id : get the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-items/{id}")
    @Timed
    public ResponseEntity<OrderItemDTO> getOrderItem(@PathVariable Long id) {
        log.debug("REST request to get OrderItem : {}", id);
        OrderItemDTO orderItemDTO = orderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderItemDTO));
    }

    /**
     * DELETE  /order-items/:id : delete the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete OrderItem : {}", id);
        orderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/order-items/master")
    @Timed
    public ResponseEntity<List<OrderItemDTO>> getOrderItemByOrderMaster(@RequestParam("id") long id) {
        log.debug("REST request to get orderItem by masterId");
        List<OrderItemDTO> orderItemDTOList = orderItemService.findByOrderMaster(id);
        HttpStatus status = orderItemDTOList != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return  new ResponseEntity<>(orderItemDTOList, status);
    }


}
