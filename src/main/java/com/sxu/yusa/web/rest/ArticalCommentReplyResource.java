package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.service.ArticalCommentReplyService;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.ArticalCommentReplyDTO;
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
 * REST controller for managing ArticalCommentReply.
 */
@RestController
@RequestMapping("/api")
public class ArticalCommentReplyResource {

    private final Logger log = LoggerFactory.getLogger(ArticalCommentReplyResource.class);

    private static final String ENTITY_NAME = "articalCommentReply";

    private final ArticalCommentReplyService articalCommentReplyService;

    public ArticalCommentReplyResource(ArticalCommentReplyService articalCommentReplyService) {
        this.articalCommentReplyService = articalCommentReplyService;
    }

    /**
     * POST  /artical-comment-replies : Create a new articalCommentReply.
     *
     * @param articalCommentReplyDTO the articalCommentReplyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articalCommentReplyDTO, or with status 400 (Bad Request) if the articalCommentReply has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artical-comment-replies")
    @Timed
    public ResponseEntity<ArticalCommentReplyDTO> createArticalCommentReply(@RequestBody ArticalCommentReplyDTO articalCommentReplyDTO) throws URISyntaxException {
        log.debug("REST request to save ArticalCommentReply : {}", articalCommentReplyDTO);
        if (articalCommentReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new articalCommentReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticalCommentReplyDTO result = articalCommentReplyService.save(articalCommentReplyDTO);
        return ResponseEntity.created(new URI("/api/artical-comment-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artical-comment-replies : Updates an existing articalCommentReply.
     *
     * @param articalCommentReplyDTO the articalCommentReplyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articalCommentReplyDTO,
     * or with status 400 (Bad Request) if the articalCommentReplyDTO is not valid,
     * or with status 500 (Internal Server Error) if the articalCommentReplyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artical-comment-replies")
    @Timed
    public ResponseEntity<ArticalCommentReplyDTO> updateArticalCommentReply(@RequestBody ArticalCommentReplyDTO articalCommentReplyDTO) throws URISyntaxException {
        log.debug("REST request to update ArticalCommentReply : {}", articalCommentReplyDTO);
        if (articalCommentReplyDTO.getId() == null) {
            return createArticalCommentReply(articalCommentReplyDTO);
        }
        ArticalCommentReplyDTO result = articalCommentReplyService.save(articalCommentReplyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articalCommentReplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artical-comment-replies : get all the articalCommentReplies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articalCommentReplies in body
     */
    @GetMapping("/artical-comment-replies")
    @Timed
    public ResponseEntity<List<ArticalCommentReplyDTO>> getAllArticalCommentReplies(Pageable pageable) {
        log.debug("REST request to get a page of ArticalCommentReplies");
        Page<ArticalCommentReplyDTO> page = articalCommentReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artical-comment-replies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artical-comment-replies/:id : get the "id" articalCommentReply.
     *
     * @param id the id of the articalCommentReplyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articalCommentReplyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/artical-comment-replies/{id}")
    @Timed
    public ResponseEntity<ArticalCommentReplyDTO> getArticalCommentReply(@PathVariable Long id) {
        log.debug("REST request to get ArticalCommentReply : {}", id);
        ArticalCommentReplyDTO articalCommentReplyDTO = articalCommentReplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articalCommentReplyDTO));
    }

    /**
     * DELETE  /artical-comment-replies/:id : delete the "id" articalCommentReply.
     *
     * @param id the id of the articalCommentReplyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artical-comment-replies/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticalCommentReply(@PathVariable Long id) {
        log.debug("REST request to delete ArticalCommentReply : {}", id);
        articalCommentReplyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
