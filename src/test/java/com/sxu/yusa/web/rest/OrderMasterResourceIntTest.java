package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.OrderMaster;
import com.sxu.yusa.repository.OrderMasterRepository;
import com.sxu.yusa.service.OrderMasterService;
import com.sxu.yusa.service.dto.OrderMasterDTO;
import com.sxu.yusa.service.mapper.OrderMasterMapper;
import com.sxu.yusa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.sxu.yusa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sxu.yusa.domain.enumeration.OrderStatus;
/**
 * Test class for the OrderMasterResource REST controller.
 *
 * @see OrderMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class OrderMasterResourceIntTest {

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICES = new BigDecimal(2);

    private static final Integer DEFAULT_TOTAL_QUANITY = 1;
    private static final Integer UPDATED_TOTAL_QUANITY = 2;

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.WAIT_TO_PAY;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.WAIT_TO_SEND;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderMasterMockMvc;

    private OrderMaster orderMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderMasterResource orderMasterResource = new OrderMasterResource(orderMasterService);
        this.restOrderMasterMockMvc = MockMvcBuilders.standaloneSetup(orderMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderMaster createEntity(EntityManager em) {
        OrderMaster orderMaster = new OrderMaster()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .totalPrices(DEFAULT_TOTAL_PRICES)
            .totalQuanity(DEFAULT_TOTAL_QUANITY)
            .orderStatus(DEFAULT_ORDER_STATUS);
        return orderMaster;
    }

    @Before
    public void initTest() {
        orderMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderMaster() throws Exception {
        int databaseSizeBeforeCreate = orderMasterRepository.findAll().size();

        // Create the OrderMaster
        OrderMasterDTO orderMasterDTO = orderMasterMapper.toDto(orderMaster);
        restOrderMasterMockMvc.perform(post("/api/order-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeCreate + 1);
        OrderMaster testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testOrderMaster.getTotalPrices()).isEqualTo(DEFAULT_TOTAL_PRICES);
        assertThat(testOrderMaster.getTotalQuanity()).isEqualTo(DEFAULT_TOTAL_QUANITY);
        assertThat(testOrderMaster.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
    }

    @Test
    @Transactional
    public void createOrderMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderMasterRepository.findAll().size();

        // Create the OrderMaster with an existing ID
        orderMaster.setId(1L);
        OrderMasterDTO orderMasterDTO = orderMasterMapper.toDto(orderMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMasterMockMvc.perform(post("/api/order-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderMasters() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMaster);

        // Get all the orderMasterList
        restOrderMasterMockMvc.perform(get("/api/order-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].totalPrices").value(hasItem(DEFAULT_TOTAL_PRICES.intValue())))
            .andExpect(jsonPath("$.[*].totalQuanity").value(hasItem(DEFAULT_TOTAL_QUANITY)))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMaster);

        // Get the orderMaster
        restOrderMasterMockMvc.perform(get("/api/order-masters/{id}", orderMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderMaster.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.totalPrices").value(DEFAULT_TOTAL_PRICES.intValue()))
            .andExpect(jsonPath("$.totalQuanity").value(DEFAULT_TOTAL_QUANITY))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderMaster() throws Exception {
        // Get the orderMaster
        restOrderMasterMockMvc.perform(get("/api/order-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMaster);
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster
        OrderMaster updatedOrderMaster = orderMasterRepository.findOne(orderMaster.getId());
        // Disconnect from session so that the updates on updatedOrderMaster are not directly saved in db
        em.detach(updatedOrderMaster);
        updatedOrderMaster
            .orderNumber(UPDATED_ORDER_NUMBER)
            .totalPrices(UPDATED_TOTAL_PRICES)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .orderStatus(UPDATED_ORDER_STATUS);
        OrderMasterDTO orderMasterDTO = orderMasterMapper.toDto(updatedOrderMaster);

        restOrderMasterMockMvc.perform(put("/api/order-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMasterDTO)))
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMaster testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testOrderMaster.getTotalPrices()).isEqualTo(UPDATED_TOTAL_PRICES);
        assertThat(testOrderMaster.getTotalQuanity()).isEqualTo(UPDATED_TOTAL_QUANITY);
        assertThat(testOrderMaster.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Create the OrderMaster
        OrderMasterDTO orderMasterDTO = orderMasterMapper.toDto(orderMaster);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderMasterMockMvc.perform(put("/api/order-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMaster);
        int databaseSizeBeforeDelete = orderMasterRepository.findAll().size();

        // Get the orderMaster
        restOrderMasterMockMvc.perform(delete("/api/order-masters/{id}", orderMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderMaster.class);
        OrderMaster orderMaster1 = new OrderMaster();
        orderMaster1.setId(1L);
        OrderMaster orderMaster2 = new OrderMaster();
        orderMaster2.setId(orderMaster1.getId());
        assertThat(orderMaster1).isEqualTo(orderMaster2);
        orderMaster2.setId(2L);
        assertThat(orderMaster1).isNotEqualTo(orderMaster2);
        orderMaster1.setId(null);
        assertThat(orderMaster1).isNotEqualTo(orderMaster2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderMasterDTO.class);
        OrderMasterDTO orderMasterDTO1 = new OrderMasterDTO();
        orderMasterDTO1.setId(1L);
        OrderMasterDTO orderMasterDTO2 = new OrderMasterDTO();
        assertThat(orderMasterDTO1).isNotEqualTo(orderMasterDTO2);
        orderMasterDTO2.setId(orderMasterDTO1.getId());
        assertThat(orderMasterDTO1).isEqualTo(orderMasterDTO2);
        orderMasterDTO2.setId(2L);
        assertThat(orderMasterDTO1).isNotEqualTo(orderMasterDTO2);
        orderMasterDTO1.setId(null);
        assertThat(orderMasterDTO1).isNotEqualTo(orderMasterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderMasterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderMasterMapper.fromId(null)).isNull();
    }
}
