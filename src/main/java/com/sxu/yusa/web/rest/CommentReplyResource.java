package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.service.CommentReplyService;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.service.dto.CommentReplyDTO;
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
 * REST controller for managing CommentReply.
 */
@RestController
@RequestMapping("/api")
public class CommentReplyResource {

    private final Logger log = LoggerFactory.getLogger(CommentReplyResource.class);

    private static final String ENTITY_NAME = "commentReply";

    private final CommentReplyService commentReplyService;

    public CommentReplyResource(CommentReplyService commentReplyService) {
        this.commentReplyService = commentReplyService;
    }

    /**
     * POST  /comment-replies : Create a new commentReply.
     *
     * @param commentReplyDTO the commentReplyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentReplyDTO, or with status 400 (Bad Request) if the commentReply has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comment-replies")
    @Timed
    public ResponseEntity<CommentReplyDTO> createCommentReply(@RequestBody CommentReplyDTO commentReplyDTO) throws URISyntaxException {
        log.debug("REST request to save CommentReply : {}", commentReplyDTO);
        if (commentReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentReplyDTO result = commentReplyService.save(commentReplyDTO);
        return ResponseEntity.created(new URI("/api/comment-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comment-replies : Updates an existing commentReply.
     *
     * @param commentReplyDTO the commentReplyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentReplyDTO,
     * or with status 400 (Bad Request) if the commentReplyDTO is not valid,
     * or with status 500 (Internal Server Error) if the commentReplyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comment-replies")
    @Timed
    public ResponseEntity<CommentReplyDTO> updateCommentReply(@RequestBody CommentReplyDTO commentReplyDTO) throws URISyntaxException {
        log.debug("REST request to update CommentReply : {}", commentReplyDTO);
        if (commentReplyDTO.getId() == null) {
            return createCommentReply(commentReplyDTO);
        }
        CommentReplyDTO result = commentReplyService.save(commentReplyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentReplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comment-replies : get all the commentReplies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commentReplies in body
     */
    @GetMapping("/comment-replies")
    @Timed
    public ResponseEntity<List<CommentReplyDTO>> getAllCommentReplies(Pageable pageable) {
        log.debug("REST request to get a page of CommentReplies");
        Page<CommentReplyDTO> page = commentReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comment-replies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comment-replies/:id : get the "id" commentReply.
     *
     * @param id the id of the commentReplyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentReplyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comment-replies/{id}")
    @Timed
    public ResponseEntity<CommentReplyDTO> getCommentReply(@PathVariable Long id) {
        log.debug("REST request to get CommentReply : {}", id);
        CommentReplyDTO commentReplyDTO = commentReplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentReplyDTO));
    }

    /**
     * DELETE  /comment-replies/:id : delete the "id" commentReply.
     *
     * @param id the id of the commentReplyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comment-replies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentReply(@PathVariable Long id) {
        log.debug("REST request to delete CommentReply : {}", id);
        commentReplyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
