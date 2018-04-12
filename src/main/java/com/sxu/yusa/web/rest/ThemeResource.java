package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.service.ThemeService;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.ThemeDTO;
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
 * REST controller for managing Theme.
 */
@RestController
@RequestMapping("/api")
public class ThemeResource {

    private final Logger log = LoggerFactory.getLogger(ThemeResource.class);

    private static final String ENTITY_NAME = "theme";

    private final ThemeService themeService;

    public ThemeResource(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * POST  /themes : Create a new theme.
     *
     * @param themeDTO the themeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new themeDTO, or with status 400 (Bad Request) if the theme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/themes")
    @Timed
    public ResponseEntity<ThemeDTO> createTheme(@RequestBody ThemeDTO themeDTO) throws URISyntaxException {
        log.debug("REST request to save Theme : {}", themeDTO);
        if (themeDTO.getId() != null) {
            throw new BadRequestAlertException("A new theme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeDTO result = themeService.save(themeDTO);
        return ResponseEntity.created(new URI("/api/themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /themes : Updates an existing theme.
     *
     * @param themeDTO the themeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated themeDTO,
     * or with status 400 (Bad Request) if the themeDTO is not valid,
     * or with status 500 (Internal Server Error) if the themeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/themes")
    @Timed
    public ResponseEntity<ThemeDTO> updateTheme(@RequestBody ThemeDTO themeDTO) throws URISyntaxException {
        log.debug("REST request to update Theme : {}", themeDTO);
        if (themeDTO.getId() == null) {
            return createTheme(themeDTO);
        }
        ThemeDTO result = themeService.save(themeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, themeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /themes : get all the themes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of themes in body
     */
    @GetMapping("/themes")
    @Timed
    public ResponseEntity<List<ThemeDTO>> getAllThemes(Pageable pageable) {
        log.debug("REST request to get a page of Themes");
        Page<ThemeDTO> page = themeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/themes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /themes/:id : get the "id" theme.
     *
     * @param id the id of the themeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the themeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/themes/{id}")
    @Timed
    public ResponseEntity<ThemeDTO> getTheme(@PathVariable Long id) {
        log.debug("REST request to get Theme : {}", id);
        ThemeDTO themeDTO = themeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(themeDTO));
    }

    /**
     * DELETE  /themes/:id : delete the "id" theme.
     *
     * @param id the id of the themeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/themes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        log.debug("REST request to delete Theme : {}", id);
        themeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
