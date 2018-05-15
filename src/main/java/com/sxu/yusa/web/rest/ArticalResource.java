package com.sxu.yusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sxu.yusa.service.ArticalService;
import com.sxu.yusa.service.dto.ArticalDTO;
import com.sxu.yusa.web.rest.errors.BadRequestAlertException;
import com.sxu.yusa.web.rest.util.HeaderUtil;
import com.sxu.yusa.web.rest.util.PaginationUtil;
import com.sxu.yusa.web.vo.ArticalVO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Artical.
 */
@RestController
@RequestMapping("/api")
public class ArticalResource {

    private final Logger log = LoggerFactory.getLogger(ArticalResource.class);

    private static final String ENTITY_NAME = "artical";

    private final ArticalService articalService;

    public ArticalResource(ArticalService articalService) {
        this.articalService = articalService;
    }

    /**
     * POST  /articals : Create a new artical.
     *
     * @param articalDTO the articalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articalDTO, or with status 400 (Bad Request) if the artical has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/articals")
    @Timed
    public ResponseEntity<ArticalDTO> createArtical(@RequestBody ArticalDTO articalDTO) throws URISyntaxException {
        log.debug("REST request to save Artical : {}", articalDTO);
        if (articalDTO.getId() != null) {
            throw new BadRequestAlertException("A new artical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        log.error(articalDTO.toString());
        ArticalDTO result = articalService.save(articalDTO);

        return ResponseEntity.created(new URI("/api/articals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /articals : Updates an existing artical.
     *
     * @param articalDTO the articalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articalDTO,
     * or with status 400 (Bad Request) if the articalDTO is not valid,
     * or with status 500 (Internal Server Error) if the articalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/articals")
    @Timed
    public ResponseEntity<ArticalDTO> updateArtical(@RequestBody ArticalDTO articalDTO) throws URISyntaxException {
        log.debug("REST request to update Artical : {}", articalDTO);
        if (articalDTO.getId() == null) {
            return createArtical(articalDTO);
        }
        ArticalDTO result = articalService.save(articalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /articals : get all the articals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articals in body
     */
    @GetMapping("/articals")
    @Timed
    public ResponseEntity<List<ArticalDTO>> getAllArticals(Pageable pageable) {
        log.debug("REST request to get a page of Articals");
        Page<ArticalDTO> page = articalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /articals/:id : get the "id" artical.
     *
     * @param id the id of the articalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/articals/{id}")
    @Timed
    public ResponseEntity<ArticalDTO> getArtical(@PathVariable Long id) {
        log.debug("REST request to get Artical : {}", id);
        ArticalDTO articalDTO = articalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(articalDTO));
    }

    /**
     * DELETE  /articals/:id : delete the "id" artical.
     *
     * @param id the id of the articalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/articals/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtical(@PathVariable Long id) {
        log.debug("REST request to delete Artical : {}", id);
        articalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @PostMapping("/articals/upload")
    public ArticalDTO upload(@RequestParam(required = false) MultipartFile file, HttpServletRequest request) {
        String basePath = request.getServletContext().getRealPath("templates/images/article");
        ArticalDTO articalDTO = new ArticalDTO();
        System.out.println(basePath);
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            String fileName = file.getOriginalFilename();

            System.out.println(file.getName() + "=========");
            String path = basePath + File.separator + fileName;
            System.out.println("=======path===" + path);
            file.transferTo(new File(path));
            articalDTO.setFigure(fileName);
            return articalDTO;

        } catch (Exception e) {
            // TODO
            System.out.println(e.getMessage());
            return articalDTO;
        }

    }
    @GetMapping("/articals/new")
    @Timed
    public List<ArticalVO> getAllByNew(){
        log.error("======="+articalService.findAllOrderByDateDesc().get(0));
        return articalService.findAllOrderByDateDesc();
    }

    @GetMapping("/articals/hot")
    @Timed
    public List<ArticalVO> getAllByHot(){
        return articalService.findAllOrderByLikeDesc();
    }
    @GetMapping("/articals/add-like")
    @Timed
    public void addLike(Long id){
        ArticalDTO articalDTO = articalService.findOne(id);
        articalDTO.setFavorite(articalDTO.getFavorite()+1);
        articalService.save(articalDTO);
    }
    @GetMapping("/articals/remove-like")
    @Timed
    public void removeLike(Long id){
        ArticalDTO articalDTO = articalService.findOne(id);
        articalDTO.setFavorite(articalDTO.getFavorite()-1);
        articalService.save(articalDTO);
    }



}
