package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.Artical;
import com.sxu.yusa.repository.ArticalRepository;
import com.sxu.yusa.service.ArticalService;
import com.sxu.yusa.service.dto.ArticalDTO;
import com.sxu.yusa.service.mapper.ArticalMapper;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.sxu.yusa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticalResource REST controller.
 *
 * @see ArticalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class ArticalResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_FIGURE = "AAAAAAAAAA";
    private static final String UPDATED_FIGURE = "BBBBBBBBBB";

    private static final Date DEFAULT_CREAT_DATE = new Date(0l);
    private static final Date UPDATED_CREAT_DATE = new Date(System.currentTimeMillis());

    @Autowired
    private ArticalRepository articalRepository;

    @Autowired
    private ArticalMapper articalMapper;

    @Autowired
    private ArticalService articalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticalMockMvc;

    private Artical artical;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticalResource articalResource = new ArticalResource(articalService);
        this.restArticalMockMvc = MockMvcBuilders.standaloneSetup(articalResource)
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
    public static Artical createEntity(EntityManager em) {
        Artical artical = new Artical()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .figure(DEFAULT_FIGURE)
            .creatDate(DEFAULT_CREAT_DATE);
        return artical;
    }

    @Before
    public void initTest() {
        artical = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtical() throws Exception {
        int databaseSizeBeforeCreate = articalRepository.findAll().size();

        // Create the Artical
        ArticalDTO articalDTO = articalMapper.toDto(artical);
        restArticalMockMvc.perform(post("/api/articals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalDTO)))
            .andExpect(status().isCreated());

        // Validate the Artical in the database
        List<Artical> articalList = articalRepository.findAll();
        assertThat(articalList).hasSize(databaseSizeBeforeCreate + 1);
        Artical testArtical = articalList.get(articalList.size() - 1);
        assertThat(testArtical.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArtical.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArtical.getFigure()).isEqualTo(DEFAULT_FIGURE);
        assertThat(testArtical.getCreatDate()).isEqualTo(DEFAULT_CREAT_DATE);
    }

    @Test
    @Transactional
    public void createArticalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articalRepository.findAll().size();

        // Create the Artical with an existing ID
        artical.setId(1L);
        ArticalDTO articalDTO = articalMapper.toDto(artical);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticalMockMvc.perform(post("/api/articals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Artical in the database
        List<Artical> articalList = articalRepository.findAll();
        assertThat(articalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticals() throws Exception {
        // Initialize the database
        articalRepository.saveAndFlush(artical);

        // Get all the articalList
        restArticalMockMvc.perform(get("/api/articals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artical.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].figure").value(hasItem(DEFAULT_FIGURE.toString())))
            .andExpect(jsonPath("$.[*].creatDate").value(hasItem(DEFAULT_CREAT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getArtical() throws Exception {
        // Initialize the database
        articalRepository.saveAndFlush(artical);

        // Get the artical
        restArticalMockMvc.perform(get("/api/articals/{id}", artical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artical.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.figure").value(DEFAULT_FIGURE.toString()))
            .andExpect(jsonPath("$.creatDate").value(DEFAULT_CREAT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArtical() throws Exception {
        // Get the artical
        restArticalMockMvc.perform(get("/api/articals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtical() throws Exception {
        // Initialize the database
        articalRepository.saveAndFlush(artical);
        int databaseSizeBeforeUpdate = articalRepository.findAll().size();

        // Update the artical
        Artical updatedArtical = articalRepository.findOne(artical.getId());
        // Disconnect from session so that the updates on updatedArtical are not directly saved in db
        em.detach(updatedArtical);
        updatedArtical
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .figure(UPDATED_FIGURE)
            .creatDate(UPDATED_CREAT_DATE);
        ArticalDTO articalDTO = articalMapper.toDto(updatedArtical);

        restArticalMockMvc.perform(put("/api/articals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalDTO)))
            .andExpect(status().isOk());

        // Validate the Artical in the database
        List<Artical> articalList = articalRepository.findAll();
        assertThat(articalList).hasSize(databaseSizeBeforeUpdate);
        Artical testArtical = articalList.get(articalList.size() - 1);
        assertThat(testArtical.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArtical.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArtical.getFigure()).isEqualTo(UPDATED_FIGURE);
        assertThat(testArtical.getCreatDate()).isEqualTo(UPDATED_CREAT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingArtical() throws Exception {
        int databaseSizeBeforeUpdate = articalRepository.findAll().size();

        // Create the Artical
        ArticalDTO articalDTO = articalMapper.toDto(artical);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticalMockMvc.perform(put("/api/articals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articalDTO)))
            .andExpect(status().isCreated());

        // Validate the Artical in the database
        List<Artical> articalList = articalRepository.findAll();
        assertThat(articalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArtical() throws Exception {
        // Initialize the database
        articalRepository.saveAndFlush(artical);
        int databaseSizeBeforeDelete = articalRepository.findAll().size();

        // Get the artical
        restArticalMockMvc.perform(delete("/api/articals/{id}", artical.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Artical> articalList = articalRepository.findAll();
        assertThat(articalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artical.class);
        Artical artical1 = new Artical();
        artical1.setId(1L);
        Artical artical2 = new Artical();
        artical2.setId(artical1.getId());
        assertThat(artical1).isEqualTo(artical2);
        artical2.setId(2L);
        assertThat(artical1).isNotEqualTo(artical2);
        artical1.setId(null);
        assertThat(artical1).isNotEqualTo(artical2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticalDTO.class);
        ArticalDTO articalDTO1 = new ArticalDTO();
        articalDTO1.setId(1L);
        ArticalDTO articalDTO2 = new ArticalDTO();
        assertThat(articalDTO1).isNotEqualTo(articalDTO2);
        articalDTO2.setId(articalDTO1.getId());
        assertThat(articalDTO1).isEqualTo(articalDTO2);
        articalDTO2.setId(2L);
        assertThat(articalDTO1).isNotEqualTo(articalDTO2);
        articalDTO1.setId(null);
        assertThat(articalDTO1).isNotEqualTo(articalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(articalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(articalMapper.fromId(null)).isNull();
    }
}
