package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.CommentReply;
import com.sxu.yusa.repository.CommentReplyRepository;
import com.sxu.yusa.service.CommentReplyService;
import com.sxu.yusa.service.dto.CommentReplyDTO;
import com.sxu.yusa.service.mapper.CommentReplyMapper;
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
 * Test class for the CommentReplyResource REST controller.
 *
 * @see CommentReplyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class CommentReplyResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREAT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREAT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Autowired
    private CommentReplyMapper commentReplyMapper;

    @Autowired
    private CommentReplyService commentReplyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentReplyMockMvc;

    private CommentReply commentReply;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentReplyResource commentReplyResource = new CommentReplyResource(commentReplyService);
        this.restCommentReplyMockMvc = MockMvcBuilders.standaloneSetup(commentReplyResource)
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
    public static CommentReply createEntity(EntityManager em) {
        CommentReply commentReply = new CommentReply()
            .content(DEFAULT_CONTENT)
            .creatDate(DEFAULT_CREAT_DATE);
        return commentReply;
    }

    @Before
    public void initTest() {
        commentReply = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentReply() throws Exception {
        int databaseSizeBeforeCreate = commentReplyRepository.findAll().size();

        // Create the CommentReply
        CommentReplyDTO commentReplyDTO = commentReplyMapper.toDto(commentReply);
        restCommentReplyMockMvc.perform(post("/api/comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the CommentReply in the database
        List<CommentReply> commentReplyList = commentReplyRepository.findAll();
        assertThat(commentReplyList).hasSize(databaseSizeBeforeCreate + 1);
        CommentReply testCommentReply = commentReplyList.get(commentReplyList.size() - 1);
        assertThat(testCommentReply.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCommentReply.getCreatDate()).isEqualTo(DEFAULT_CREAT_DATE);
    }

    @Test
    @Transactional
    public void createCommentReplyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentReplyRepository.findAll().size();

        // Create the CommentReply with an existing ID
        commentReply.setId(1L);
        CommentReplyDTO commentReplyDTO = commentReplyMapper.toDto(commentReply);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentReplyMockMvc.perform(post("/api/comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentReplyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommentReply in the database
        List<CommentReply> commentReplyList = commentReplyRepository.findAll();
        assertThat(commentReplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommentReplies() throws Exception {
        // Initialize the database
        commentReplyRepository.saveAndFlush(commentReply);

        // Get all the commentReplyList
        restCommentReplyMockMvc.perform(get("/api/comment-replies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentReply.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].creatDate").value(hasItem(DEFAULT_CREAT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getCommentReply() throws Exception {
        // Initialize the database
        commentReplyRepository.saveAndFlush(commentReply);

        // Get the commentReply
        restCommentReplyMockMvc.perform(get("/api/comment-replies/{id}", commentReply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentReply.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.creatDate").value(DEFAULT_CREAT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommentReply() throws Exception {
        // Get the commentReply
        restCommentReplyMockMvc.perform(get("/api/comment-replies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentReply() throws Exception {
        // Initialize the database
        commentReplyRepository.saveAndFlush(commentReply);
        int databaseSizeBeforeUpdate = commentReplyRepository.findAll().size();

        // Update the commentReply
        CommentReply updatedCommentReply = commentReplyRepository.findOne(commentReply.getId());
        // Disconnect from session so that the updates on updatedCommentReply are not directly saved in db
        em.detach(updatedCommentReply);
        updatedCommentReply
            .content(UPDATED_CONTENT)
            .creatDate(UPDATED_CREAT_DATE);
        CommentReplyDTO commentReplyDTO = commentReplyMapper.toDto(updatedCommentReply);

        restCommentReplyMockMvc.perform(put("/api/comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentReplyDTO)))
            .andExpect(status().isOk());

        // Validate the CommentReply in the database
        List<CommentReply> commentReplyList = commentReplyRepository.findAll();
        assertThat(commentReplyList).hasSize(databaseSizeBeforeUpdate);
        CommentReply testCommentReply = commentReplyList.get(commentReplyList.size() - 1);
        assertThat(testCommentReply.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCommentReply.getCreatDate()).isEqualTo(UPDATED_CREAT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentReply() throws Exception {
        int databaseSizeBeforeUpdate = commentReplyRepository.findAll().size();

        // Create the CommentReply
        CommentReplyDTO commentReplyDTO = commentReplyMapper.toDto(commentReply);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentReplyMockMvc.perform(put("/api/comment-replies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentReplyDTO)))
            .andExpect(status().isCreated());

        // Validate the CommentReply in the database
        List<CommentReply> commentReplyList = commentReplyRepository.findAll();
        assertThat(commentReplyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentReply() throws Exception {
        // Initialize the database
        commentReplyRepository.saveAndFlush(commentReply);
        int databaseSizeBeforeDelete = commentReplyRepository.findAll().size();

        // Get the commentReply
        restCommentReplyMockMvc.perform(delete("/api/comment-replies/{id}", commentReply.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentReply> commentReplyList = commentReplyRepository.findAll();
        assertThat(commentReplyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentReply.class);
        CommentReply commentReply1 = new CommentReply();
        commentReply1.setId(1L);
        CommentReply commentReply2 = new CommentReply();
        commentReply2.setId(commentReply1.getId());
        assertThat(commentReply1).isEqualTo(commentReply2);
        commentReply2.setId(2L);
        assertThat(commentReply1).isNotEqualTo(commentReply2);
        commentReply1.setId(null);
        assertThat(commentReply1).isNotEqualTo(commentReply2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentReplyDTO.class);
        CommentReplyDTO commentReplyDTO1 = new CommentReplyDTO();
        commentReplyDTO1.setId(1L);
        CommentReplyDTO commentReplyDTO2 = new CommentReplyDTO();
        assertThat(commentReplyDTO1).isNotEqualTo(commentReplyDTO2);
        commentReplyDTO2.setId(commentReplyDTO1.getId());
        assertThat(commentReplyDTO1).isEqualTo(commentReplyDTO2);
        commentReplyDTO2.setId(2L);
        assertThat(commentReplyDTO1).isNotEqualTo(commentReplyDTO2);
        commentReplyDTO1.setId(null);
        assertThat(commentReplyDTO1).isNotEqualTo(commentReplyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commentReplyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commentReplyMapper.fromId(null)).isNull();
    }
}
