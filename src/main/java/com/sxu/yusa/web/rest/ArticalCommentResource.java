package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.service.ArticalCommentService;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.ArticalCommentDTO;
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
 * REST controller for managing ArticalComment.
 */
@RestController
@RequestMapping("/api")
public class ArticalCommentResource {

    private final Logger log = LoggerFactory.getLogger(ArticalCommentResource.class);

    private static final String ENTITY_NAME = "articalComment";

    private final ArticalCommentService articalCommentService;

    public ArticalCommentResource(ArticalCommentService articalCommentService) {
        this.articalCommentService = articalCommentService;
    }

    /**
     * POST  /artical-comments : Create a new articalComment.
     *
     * @param articalCommentDTO the articalCommentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articalCommentDTO, or with status 400 (Bad Request) if the articalComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artical-comments")
    @Timed
    public ResponseEntity<ArticalCommentDTO> createArticalComment(@RequestBody ArticalCommentDTO articalCommentDTO) throws URISyntaxException {
        log.debug("REST request to save ArticalComment : {}", articalCommentDTO);
        if (articalCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new articalComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticalCommentDTO result = articalCommentService.save(articalCommentDTO);
        return ResponseEntity.created(new URI("/api/artical-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artical-comments : Updates an existing articalComment.
     *
     * @param articalCommentDTO the articalCommentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articalCommentDTO,
     * or with status 400 (Bad Request) if the articalCommentDTO is not valid,
     * or with status 500 (Internal Server Error) if the articalCommentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artical-comments")
    @Timed
    public ResponseEntity<ArticalCommentDTO> updateArticalComment(@RequestBody ArticalCommentDTO articalCommentDTO) throws URISyntaxException {
        log.debug("REST request to update ArticalComment : {}", articalCommentDTO);
        if (articalCommentDTO.getId() == null) {
            return createArticalComment(articalCommentDTO);
        }
        ArticalCommentDTO result = articalCommentService.save(articalCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articalCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artical-comments : get all the articalComments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articalComments in body
     */
    @GetMapping("/artical-comments")
    @Timed
    public ResponseEntity<List<ArticalCommentDTO>> getAllArticalComments(Pageable pageable) {
        log.debug("REST request to get a page of ArticalComments");
        Page<ArticalCommentDTO> page = articalCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artical-comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artical-comments/:id : get the "id" articalComment.
     *
     * @param id the id of the articalCommentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articalCommentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/artical-comments/{id}")
    @Timed
    public ResponseEntity<ArticalCommentDTO> getArticalComment(@PathVariable Long id) {
        log.debug("REST request to get ArticalComment : {}", id);
        ArticalCommentDTO articalCommentDTO = articalCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articalCommentDTO));
    }

    /**
     * DELETE  /artical-comments/:id : delete the "id" articalComment.
     *
     * @param id the id of the articalCommentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artical-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticalComment(@PathVariable Long id) {
        log.debug("REST request to delete ArticalComment : {}", id);
        articalCommentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
