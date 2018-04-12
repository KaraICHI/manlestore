package com.sxu.yusa.web.rest;

import com.sxu.yusa.ManleApp;

import com.sxu.yusa.domain.ClientUser;
import com.sxu.yusa.repository.ClientUserRepository;
import com.sxu.yusa.service.ClientUserService;
import com.sxu.yusa.service.dto.ClientUserDTO;
import com.sxu.yusa.service.mapper.ClientUserMapper;
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
 * Test class for the ClientUserResource REST controller.
 *
 * @see ClientUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManleApp.class)
public class ClientUserResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIGURE = "AAAAAAAAAA";
    private static final String UPDATED_FIGURE = "BBBBBBBBBB";

    private static final Float DEFAULT_POINT = 1F;
    private static final Float UPDATED_POINT = 2F;

    @Autowired
    private ClientUserRepository clientUserRepository;

    @Autowired
    private ClientUserMapper clientUserMapper;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientUserMockMvc;

    private ClientUser clientUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientUserResource clientUserResource = new ClientUserResource(clientUserService);
        this.restClientUserMockMvc = MockMvcBuilders.standaloneSetup(clientUserResource)
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
    public static ClientUser createEntity(EntityManager em) {
        ClientUser clientUser = new ClientUser()
            .userName(DEFAULT_USER_NAME)
            .phone(DEFAULT_PHONE)
            .password(DEFAULT_PASSWORD)
            .figure(DEFAULT_FIGURE)
            .point(DEFAULT_POINT);
        return clientUser;
    }

    @Before
    public void initTest() {
        clientUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientUser() throws Exception {
        int databaseSizeBeforeCreate = clientUserRepository.findAll().size();

        // Create the ClientUser
        ClientUserDTO clientUserDTO = clientUserMapper.toDto(clientUser);
        restClientUserMockMvc.perform(post("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeCreate + 1);
        ClientUser testClientUser = clientUserList.get(clientUserList.size() - 1);
        assertThat(testClientUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testClientUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClientUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testClientUser.getFigure()).isEqualTo(DEFAULT_FIGURE);
        assertThat(testClientUser.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    public void createClientUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientUserRepository.findAll().size();

        // Create the ClientUser with an existing ID
        clientUser.setId(1L);
        ClientUserDTO clientUserDTO = clientUserMapper.toDto(clientUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientUserMockMvc.perform(post("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientUserRepository.findAll().size();
        // set the field null
        clientUser.setPhone(null);

        // Create the ClientUser, which fails.
        ClientUserDTO clientUserDTO = clientUserMapper.toDto(clientUser);

        restClientUserMockMvc.perform(post("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUserDTO)))
            .andExpect(status().isBadRequest());

        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientUserRepository.findAll().size();
        // set the field null
        clientUser.setPassword(null);

        // Create the ClientUser, which fails.
        ClientUserDTO clientUserDTO = clientUserMapper.toDto(clientUser);

        restClientUserMockMvc.perform(post("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUserDTO)))
            .andExpect(status().isBadRequest());

        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientUsers() throws Exception {
        // Initialize the database
        clientUserRepository.saveAndFlush(clientUser);

        // Get all the clientUserList
        restClientUserMockMvc.perform(get("/api/client-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].figure").value(hasItem(DEFAULT_FIGURE.toString())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT.doubleValue())));
    }

    @Test
    @Transactional
    public void getClientUser() throws Exception {
        // Initialize the database
        clientUserRepository.saveAndFlush(clientUser);

        // Get the clientUser
        restClientUserMockMvc.perform(get("/api/client-users/{id}", clientUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.figure").value(DEFAULT_FIGURE.toString()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientUser() throws Exception {
        // Get the clientUser
        restClientUserMockMvc.perform(get("/api/client-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientUser() throws Exception {
        // Initialize the database
        clientUserRepository.saveAndFlush(clientUser);
        int databaseSizeBeforeUpdate = clientUserRepository.findAll().size();

        // Update the clientUser
        ClientUser updatedClientUser = clientUserRepository.findOne(clientUser.getId());
        // Disconnect from session so that the updates on updatedClientUser are not directly saved in db
        em.detach(updatedClientUser);
        updatedClientUser
            .userName(UPDATED_USER_NAME)
            .phone(UPDATED_PHONE)
            .password(UPDATED_PASSWORD)
            .figure(UPDATED_FIGURE)
            .point(UPDATED_POINT);
        ClientUserDTO clientUserDTO = clientUserMapper.toDto(updatedClientUser);

        restClientUserMockMvc.perform(put("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUserDTO)))
            .andExpect(status().isOk());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeUpdate);
        ClientUser testClientUser = clientUserList.get(clientUserList.size() - 1);
        assertThat(testClientUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testClientUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClientUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testClientUser.getFigure()).isEqualTo(UPDATED_FIGURE);
        assertThat(testClientUser.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    public void updateNonExistingClientUser() throws Exception {
        int databaseSizeBeforeUpdate = clientUserRepository.findAll().size();

        // Create the ClientUser
        ClientUserDTO clientUserDTO = clientUserMapper.toDto(clientUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientUserMockMvc.perform(put("/api/client-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientUser in the database
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientUser() throws Exception {
        // Initialize the database
        clientUserRepository.saveAndFlush(clientUser);
        int databaseSizeBeforeDelete = clientUserRepository.findAll().size();

        // Get the clientUser
        restClientUserMockMvc.perform(delete("/api/client-users/{id}", clientUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientUser> clientUserList = clientUserRepository.findAll();
        assertThat(clientUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientUser.class);
        ClientUser clientUser1 = new ClientUser();
        clientUser1.setId(1L);
        ClientUser clientUser2 = new ClientUser();
        clientUser2.setId(clientUser1.getId());
        assertThat(clientUser1).isEqualTo(clientUser2);
        clientUser2.setId(2L);
        assertThat(clientUser1).isNotEqualTo(clientUser2);
        clientUser1.setId(null);
        assertThat(clientUser1).isNotEqualTo(clientUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientUserDTO.class);
        ClientUserDTO clientUserDTO1 = new ClientUserDTO();
        clientUserDTO1.setId(1L);
        ClientUserDTO clientUserDTO2 = new ClientUserDTO();
        assertThat(clientUserDTO1).isNotEqualTo(clientUserDTO2);
        clientUserDTO2.setId(clientUserDTO1.getId());
        assertThat(clientUserDTO1).isEqualTo(clientUserDTO2);
        clientUserDTO2.setId(2L);
        assertThat(clientUserDTO1).isNotEqualTo(clientUserDTO2);
        clientUserDTO1.setId(null);
        assertThat(clientUserDTO1).isNotEqualTo(clientUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientUserMapper.fromId(null)).isNull();
    }
}
