package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sxu.yusa.domain.ClientUser;
import com.sxu.yusa.service.ClientUserService;
import com.sxu.yusa.service.dto.ProductDTO;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.ClientUserDTO;
import com.sxu.yusa.web.vo.ClientUserVO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClientUser.
 */
@RestController
@RequestMapping("/api")
public class ClientUserResource {

    private final Logger log = LoggerFactory.getLogger(ClientUserResource.class);

    private static final String ENTITY_NAME = "clientUser";

    private final ClientUserService clientUserService;

    public ClientUserResource(ClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }

    /**
     * POST  /client-users : Create a new clientUser.
     *
     * @param clientUserDTO the clientUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientUserDTO, or with status 400 (Bad Request) if the clientUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-users")
    @Timed
    public ResponseEntity<ClientUserDTO> createClientUser(@Valid @RequestBody ClientUserDTO clientUserDTO) throws URISyntaxException {
        log.debug("REST request to save ClientUser : {}", clientUserDTO);
        if (clientUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientUserDTO result = clientUserService.save(clientUserDTO);
        return ResponseEntity.created(new URI("/api/client-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-users : Updates an existing clientUser.
     *
     * @param clientUserDTO the clientUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientUserDTO,
     * or with status 400 (Bad Request) if the clientUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-users")
    @Timed
    public ResponseEntity<ClientUserDTO> updateClientUser(@Valid @RequestBody ClientUserDTO clientUserDTO) throws URISyntaxException {
        log.debug("REST request to update ClientUser : {}", clientUserDTO);
        if (clientUserDTO.getId() == null) {
            return createClientUser(clientUserDTO);
        }
        ClientUserDTO result = clientUserService.save(clientUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-users : get all the clientUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientUsers in body
     */
    @GetMapping("/client-users")
    @Timed
    public ResponseEntity<List<ClientUserDTO>> getAllClientUsers(Pageable pageable) {
        log.debug("REST request to get a page of ClientUsers");
        Page<ClientUserDTO> page = clientUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-users/:id : get the "id" clientUser.
     *
     * @param id the id of the clientUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/client-users/{id}")
    @Timed
    public ResponseEntity<ClientUserDTO> getClientUser(@PathVariable Long id) {
        log.debug("REST request to get ClientUser : {}", id);
        ClientUserDTO clientUserDTO = clientUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientUserDTO));
    }

    /**
     * DELETE  /client-users/:id : delete the "id" clientUser.
     *
     * @param id the id of the clientUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientUser(@PathVariable Long id) {
        log.debug("REST request to delete ClientUser : {}", id);
        clientUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/client-users/login")
    @Timed
    public ClientUserDTO login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return  clientUserService.login(phone,password);
    }
    @PostMapping("/client-users/upload")
    public ClientUserDTO upload(@RequestParam(required = false) MultipartFile file, Long id, HttpServletRequest request) {
        String basePath = request.getServletContext().getRealPath("templates/images/avatar");
        ClientUserDTO clientUserDTO = clientUserService.findOne(id);
        System.out.println(basePath);
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            String fileName=file.getOriginalFilename();

            System.out.println(file.getName()+"=========");
            String path = basePath +File.separator+ fileName;
            System.out.println("=======path==="+path);
            file.transferTo(new File(path));
            if (clientUserDTO!=null){
                clientUserDTO.setFigure(fileName);
                clientUserService.save(clientUserDTO);
            }else {
                clientUserDTO = new ClientUserDTO();
                clientUserDTO.setFigure(fileName);
            }

            return clientUserDTO;

        } catch (Exception e) {
            // TODO
            System.out.println(e.getMessage());
            return clientUserDTO;
        }

    }

}
