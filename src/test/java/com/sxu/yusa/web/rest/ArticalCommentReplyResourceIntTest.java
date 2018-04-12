package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.ArticalCommentReply;
import com.sxu.yusa.repository.ArticalCommentReplyRepository;
import com.sxu.yusa.service.ArticalCommentReplyService;
import com.sxu.yusa.service.dto.ArticalCommentReplyDTO;
import com.sxu.yusa.service.mapper.ArticalCommentReplyMapper;
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
 * Test class for the ArticalCommentReplyResource REST controller.
 *
 * @see ArticalCommentReplyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class ArticalCommentReplyResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREAT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREAT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ArticalCommentReplyRepository articalCommentReplyRepository;

    @Autowired
    private ArticalCommentReplyMapper articalCommentReplyMapper;

    @Autowired
    private ArticalCommentReplyService articalCommentReplyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticalCommentReplyMockMvc;

    private ArticalCommentReply articalCommentReply;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticalCommentReplyResource articalCommentReplyResource = new ArticalCommentReplyResource(articalCommentReplyService);
        this.restArticalCommentReplyMockMvc = MockMvcBuilders.standaloneSetup(articalCommentReplyResource)
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
    public static ArticalCommentReply createEntity(EntityManager em) {
        ArticalCommentReply articalCommentReply = new ArticalCommentReply()
            .content(DEFAULT_CONTENT)
            .creatDate(DEFAULT_CREAT_DATE);
        return articalCommentReply;
    }

    @Before
    public void initTest() {
        articalCommentReply = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticalCommentReply() throws Exception {
        int databaseSizeBeforeCreate = articalCommentReplyRepository.findAll().size();

        // Create the ArticalCommentReply
        ArticalCommentReplyDTO articalCommentReplyDTO = articalCommentReplyMapper.toDto(articalCommentReply);
        restArticalCommentReplyMockMvc.perform(post("/api/artical-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticalCommentReply in the database
        List<ArticalCommentReply> articalCommentReplyList = articalCommentReplyRepository.findAll();
        assertThat(articalCommentReplyList).hasSize(databaseSizeBeforeCreate + 1);
        ArticalCommentReply testArticalCommentReply = articalCommentReplyList.get(articalCommentReplyList.size() - 1);
        assertThat(testArticalCommentReply.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArticalCommentReply.getCreatDate()).isEqualTo(DEFAULT_CREAT_DATE);
    }

    @Test
    @Transactional
    public void createArticalCommentReplyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articalCommentReplyRepository.findAll().size();

        // Create the ArticalCommentReply with an existing ID
        articalCommentReply.setId(1L);
        ArticalCommentReplyDTO articalCommentReplyDTO = articalCommentReplyMapper.toDto(articalCommentReply);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticalCommentReplyMockMvc.perform(post("/api/artical-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentReplyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticalCommentReply in the database
        List<ArticalCommentReply> articalCommentReplyList = articalCommentReplyRepository.findAll();
        assertThat(articalCommentReplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticalCommentReplies() throws Exception {
        // Initialize the database
        articalCommentReplyRepository.saveAndFlush(articalCommentReply);

        // Get all the articalCommentReplyList
        restArticalCommentReplyMockMvc.perform(get("/api/artical-comment-replies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articalCommentReply.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].creatDate").value(hasItem(DEFAULT_CREAT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getArticalCommentReply() throws Exception {
        // Initialize the database
        articalCommentReplyRepository.saveAndFlush(articalCommentReply);

        // Get the articalCommentReply
        restArticalCommentReplyMockMvc.perform(get("/api/artical-comment-replies/{id}", articalCommentReply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articalCommentReply.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.creatDate").value(DEFAULT_CREAT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticalCommentReply() throws Exception {
        // Get the articalCommentReply
        restArticalCommentReplyMockMvc.perform(get("/api/artical-comment-replies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticalCommentReply() throws Exception {
        // Initialize the database
        articalCommentReplyRepository.saveAndFlush(articalCommentReply);
        int databaseSizeBeforeUpdate = articalCommentReplyRepository.findAll().size();

        // Update the articalCommentReply
        ArticalCommentReply updatedArticalCommentReply = articalCommentReplyRepository.findOne(articalCommentReply.getId());
        // Disconnect from session so that the updates on updatedArticalCommentReply are not directly saved in db
        em.detach(updatedArticalCommentReply);
        updatedArticalCommentReply
            .content(UPDATED_CONTENT)
            .creatDate(UPDATED_CREAT_DATE);
        ArticalCommentReplyDTO articalCommentReplyDTO = articalCommentReplyMapper.toDto(updatedArticalCommentReply);

        restArticalCommentReplyMockMvc.perform(put("/api/artical-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentReplyDTO)))
            .andExpect(status().isOk());

        // Validate the ArticalCommentReply in the database
        List<ArticalCommentReply> articalCommentReplyList = articalCommentReplyRepository.findAll();
        assertThat(articalCommentReplyList).hasSize(databaseSizeBeforeUpdate);
        ArticalCommentReply testArticalCommentReply = articalCommentReplyList.get(articalCommentReplyList.size() - 1);
        assertThat(testArticalCommentReply.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArticalCommentReply.getCreatDate()).isEqualTo(UPDATED_CREAT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingArticalCommentReply() throws Exception {
        int databaseSizeBeforeUpdate = articalCommentReplyRepository.findAll().size();

        // Create the ArticalCommentReply
        ArticalCommentReplyDTO articalCommentReplyDTO = articalCommentReplyMapper.toDto(articalCommentReply);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticalCommentReplyMockMvc.perform(put("/api/artical-comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticalCommentReply in the database
        List<ArticalCommentReply> articalCommentReplyList = articalCommentReplyRepository.findAll();
        assertThat(articalCommentReplyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticalCommentReply() throws Exception {
        // Initialize the database
        articalCommentReplyRepository.saveAndFlush(articalCommentReply);
        int databaseSizeBeforeDelete = articalCommentReplyRepository.findAll().size();

        // Get the articalCommentReply
        restArticalCommentReplyMockMvc.perform(delete("/api/artical-comment-replies/{id}", articalCommentReply.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticalCommentReply> articalCommentReplyList = articalCommentReplyRepository.findAll();
        assertThat(articalCommentReplyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticalCommentReply.class);
        ArticalCommentReply articalCommentReply1 = new ArticalCommentReply();
        articalCommentReply1.setId(1L);
        ArticalCommentReply articalCommentReply2 = new ArticalCommentReply();
        articalCommentReply2.setId(articalCommentReply1.getId());
        assertThat(articalCommentReply1).isEqualTo(articalCommentReply2);
        articalCommentReply2.setId(2L);
        assertThat(articalCommentReply1).isNotEqualTo(articalCommentReply2);
        articalCommentReply1.setId(null);
        assertThat(articalCommentReply1).isNotEqualTo(articalCommentReply2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticalCommentReplyDTO.class);
        ArticalCommentReplyDTO articalCommentReplyDTO1 = new ArticalCommentReplyDTO();
        articalCommentReplyDTO1.setId(1L);
        ArticalCommentReplyDTO articalCommentReplyDTO2 = new ArticalCommentReplyDTO();
        assertThat(articalCommentReplyDTO1).isNotEqualTo(articalCommentReplyDTO2);
        articalCommentReplyDTO2.setId(articalCommentReplyDTO1.getId());
        assertThat(articalCommentReplyDTO1).isEqualTo(articalCommentReplyDTO2);
        articalCommentReplyDTO2.setId(2L);
        assertThat(articalCommentReplyDTO1).isNotEqualTo(articalCommentReplyDTO2);
        articalCommentReplyDTO1.setId(null);
        assertThat(articalCommentReplyDTO1).isNotEqualTo(articalCommentReplyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(articalCommentReplyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(articalCommentReplyMapper.fromId(null)).isNull();
    }
}
