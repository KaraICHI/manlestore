package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.ArticalComment;
import com.sxu.yusa.repository.ArticalCommentRepository;
import com.sxu.yusa.service.ArticalCommentService;
import com.sxu.yusa.service.dto.ArticalCommentDTO;
import com.sxu.yusa.service.mapper.ArticalCommentMapper;
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
import java.util.List;

import static com.sxu.yusa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticalCommentResource REST controller.
 *
 * @see ArticalCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class ArticalCommentResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private ArticalCommentRepository articalCommentRepository;

    @Autowired
    private ArticalCommentMapper articalCommentMapper;

    @Autowired
    private ArticalCommentService articalCommentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticalCommentMockMvc;

    private ArticalComment articalComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticalCommentResource articalCommentResource = new ArticalCommentResource(articalCommentService);
        this.restArticalCommentMockMvc = MockMvcBuilders.standaloneSetup(articalCommentResource)
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
    public static ArticalComment createEntity(EntityManager em) {
        ArticalComment articalComment = new ArticalComment()
            .content(DEFAULT_CONTENT);
        return articalComment;
    }

    @Before
    public void initTest() {
        articalComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticalComment() throws Exception {
        int databaseSizeBeforeCreate = articalCommentRepository.findAll().size();

        // Create the ArticalComment
        ArticalCommentDTO articalCommentDTO = articalCommentMapper.toDto(articalComment);
        restArticalCommentMockMvc.perform(post("/api/artical-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticalComment in the database
        List<ArticalComment> articalCommentList = articalCommentRepository.findAll();
        assertThat(articalCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ArticalComment testArticalComment = articalCommentList.get(articalCommentList.size() - 1);
        assertThat(testArticalComment.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createArticalCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articalCommentRepository.findAll().size();

        // Create the ArticalComment with an existing ID
        articalComment.setId(1L);
        ArticalCommentDTO articalCommentDTO = articalCommentMapper.toDto(articalComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticalCommentMockMvc.perform(post("/api/artical-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticalComment in the database
        List<ArticalComment> articalCommentList = articalCommentRepository.findAll();
        assertThat(articalCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticalComments() throws Exception {
        // Initialize the database
        articalCommentRepository.saveAndFlush(articalComment);

        // Get all the articalCommentList
        restArticalCommentMockMvc.perform(get("/api/artical-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articalComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getArticalComment() throws Exception {
        // Initialize the database
        articalCommentRepository.saveAndFlush(articalComment);

        // Get the articalComment
        restArticalCommentMockMvc.perform(get("/api/artical-comments/{id}", articalComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articalComment.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticalComment() throws Exception {
        // Get the articalComment
        restArticalCommentMockMvc.perform(get("/api/artical-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticalComment() throws Exception {
        // Initialize the database
        articalCommentRepository.saveAndFlush(articalComment);
        int databaseSizeBeforeUpdate = articalCommentRepository.findAll().size();

        // Update the articalComment
        ArticalComment updatedArticalComment = articalCommentRepository.findOne(articalComment.getId());
        // Disconnect from session so that the updates on updatedArticalComment are not directly saved in db
        em.detach(updatedArticalComment);
        updatedArticalComment
            .content(UPDATED_CONTENT);
        ArticalCommentDTO articalCommentDTO = articalCommentMapper.toDto(updatedArticalComment);

        restArticalCommentMockMvc.perform(put("/api/artical-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentDTO)))
            .andExpect(status().isOk());

        // Validate the ArticalComment in the database
        List<ArticalComment> articalCommentList = articalCommentRepository.findAll();
        assertThat(articalCommentList).hasSize(databaseSizeBeforeUpdate);
        ArticalComment testArticalComment = articalCommentList.get(articalCommentList.size() - 1);
        assertThat(testArticalComment.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingArticalComment() throws Exception {
        int databaseSizeBeforeUpdate = articalCommentRepository.findAll().size();

        // Create the ArticalComment
        ArticalCommentDTO articalCommentDTO = articalCommentMapper.toDto(articalComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticalCommentMockMvc.perform(put("/api/artical-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticalComment in the database
        List<ArticalComment> articalCommentList = articalCommentRepository.findAll();
        assertThat(articalCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticalComment() throws Exception {
        // Initialize the database
        articalCommentRepository.saveAndFlush(articalComment);
        int databaseSizeBeforeDelete = articalCommentRepository.findAll().size();

        // Get the articalComment
        restArticalCommentMockMvc.perform(delete("/api/artical-comments/{id}", articalComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArticalComment> articalCommentList = articalCommentRepository.findAll();
        assertThat(articalCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticalComment.class);
        ArticalComment articalComment1 = new ArticalComment();
        articalComment1.setId(1L);
        ArticalComment articalComment2 = new ArticalComment();
        articalComment2.setId(articalComment1.getId());
        assertThat(articalComment1).isEqualTo(articalComment2);
        articalComment2.setId(2L);
        assertThat(articalComment1).isNotEqualTo(articalComment2);
        articalComment1.setId(null);
        assertThat(articalComment1).isNotEqualTo(articalComment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticalCommentDTO.class);
        ArticalCommentDTO articalCommentDTO1 = new ArticalCommentDTO();
        articalCommentDTO1.setId(1L);
        ArticalCommentDTO articalCommentDTO2 = new ArticalCommentDTO();
        assertThat(articalCommentDTO1).isNotEqualTo(articalCommentDTO2);
        articalCommentDTO2.setId(articalCommentDTO1.getId());
        assertThat(articalCommentDTO1).isEqualTo(articalCommentDTO2);
        articalCommentDTO2.setId(2L);
        assertThat(articalCommentDTO1).isNotEqualTo(articalCommentDTO2);
        articalCommentDTO1.setId(null);
        assertThat(articalCommentDTO1).isNotEqualTo(articalCommentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(articalCommentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(articalCommentMapper.fromId(null)).isNull();
    }
}
