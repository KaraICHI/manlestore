package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.OrderComment;
import com.sxu.yusa.repository.OrderCommentRepository;
import com.sxu.yusa.service.OrderCommentService;
import com.sxu.yusa.service.dto.OrderCommentDTO;
import com.sxu.yusa.service.mapper.OrderCommentMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.sxu.yusa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderCommentResource REST controller.
 *
 * @see OrderCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class OrderCommentResourceIntTest {

    private static final Float DEFAULT_LEVEL = 1F;
    private static final Float UPDATED_LEVEL = 2F;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREAT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREAT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderCommentRepository orderCommentRepository;

    @Autowired
    private OrderCommentMapper orderCommentMapper;

    @Autowired
    private OrderCommentService orderCommentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderCommentMockMvc;

    private OrderComment orderComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderCommentResource orderCommentResource = new OrderCommentResource(orderCommentService);
        this.restOrderCommentMockMvc = MockMvcBuilders.standaloneSetup(orderCommentResource)
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
    public static OrderComment createEntity(EntityManager em) {
        OrderComment orderComment = new OrderComment()
            .level(DEFAULT_LEVEL)
            .content(DEFAULT_CONTENT)
            .creatDate(DEFAULT_CREAT_DATE);
        return orderComment;
    }

    @Before
    public void initTest() {
        orderComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderComment() throws Exception {
        int databaseSizeBeforeCreate = orderCommentRepository.findAll().size();

        // Create the OrderComment
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);
        restOrderCommentMockMvc.perform(post("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeCreate + 1);
        OrderComment testOrderComment = orderCommentList.get(orderCommentList.size() - 1);
        assertThat(testOrderComment.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testOrderComment.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testOrderComment.getCreatDate()).isEqualTo(DEFAULT_CREAT_DATE);
    }

    @Test
    @Transactional
    public void createOrderCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderCommentRepository.findAll().size();

        // Create the OrderComment with an existing ID
        orderComment.setId(1L);
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderCommentMockMvc.perform(post("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderCommentRepository.findAll().size();
        // set the field null
        orderComment.setLevel(null);

        // Create the OrderComment, which fails.
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);

        restOrderCommentMockMvc.perform(post("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isBadRequest());

        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderComments() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);

        // Get all the orderCommentList
        restOrderCommentMockMvc.perform(get("/api/order-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].creatDate").value(hasItem(DEFAULT_CREAT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getOrderComment() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);

        // Get the orderComment
        restOrderCommentMockMvc.perform(get("/api/order-comments/{id}", orderComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderComment.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.creatDate").value(DEFAULT_CREAT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderComment() throws Exception {
        // Get the orderComment
        restOrderCommentMockMvc.perform(get("/api/order-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderComment() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);
        int databaseSizeBeforeUpdate = orderCommentRepository.findAll().size();

        // Update the orderComment
        OrderComment updatedOrderComment = orderCommentRepository.findOne(orderComment.getId());
        // Disconnect from session so that the updates on updatedOrderComment are not directly saved in db
        em.detach(updatedOrderComment);
        updatedOrderComment
            .level(UPDATED_LEVEL)
            .content(UPDATED_CONTENT)
            .creatDate(UPDATED_CREAT_DATE);
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(updatedOrderComment);

        restOrderCommentMockMvc.perform(put("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isOk());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeUpdate);
        OrderComment testOrderComment = orderCommentList.get(orderCommentList.size() - 1);
        assertThat(testOrderComment.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testOrderComment.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testOrderComment.getCreatDate()).isEqualTo(UPDATED_CREAT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderComment() throws Exception {
        int databaseSizeBeforeUpdate = orderCommentRepository.findAll().size();

        // Create the OrderComment
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderCommentMockMvc.perform(put("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderComment() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);
        int databaseSizeBeforeDelete = orderCommentRepository.findAll().size();

        // Get the orderComment
        restOrderCommentMockMvc.perform(delete("/api/order-comments/{id}", orderComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderComment.class);
        OrderComment orderComment1 = new OrderComment();
        orderComment1.setId(1L);
        OrderComment orderComment2 = new OrderComment();
        orderComment2.setId(orderComment1.getId());
        assertThat(orderComment1).isEqualTo(orderComment2);
        orderComment2.setId(2L);
        assertThat(orderComment1).isNotEqualTo(orderComment2);
        orderComment1.setId(null);
        assertThat(orderComment1).isNotEqualTo(orderComment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCommentDTO.class);
        OrderCommentDTO orderCommentDTO1 = new OrderCommentDTO();
        orderCommentDTO1.setId(1L);
        OrderCommentDTO orderCommentDTO2 = new OrderCommentDTO();
        assertThat(orderCommentDTO1).isNotEqualTo(orderCommentDTO2);
        orderCommentDTO2.setId(orderCommentDTO1.getId());
        assertThat(orderCommentDTO1).isEqualTo(orderCommentDTO2);
        orderCommentDTO2.setId(2L);
        assertThat(orderCommentDTO1).isNotEqualTo(orderCommentDTO2);
        orderCommentDTO1.setId(null);
        assertThat(orderCommentDTO1).isNotEqualTo(orderCommentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderCommentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderCommentMapper.fromId(null)).isNull();
    }
}
