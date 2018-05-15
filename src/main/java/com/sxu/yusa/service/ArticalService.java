package com.sxu.yusa.service;

import com.sxu.yusa.service.dto.ArticalDTO;
import com.sxu.yusa.web.vo.ArticalVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing Artical.
 */
public interface ArticalService {

    /**
     * Save a artical.
     *
     * @param articalDTO the entity to save
     * @return the persisted entity
     */
    ArticalDTO save(ArticalDTO articalDTO);

    /**
     * Get all the articals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ArticalDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artical.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ArticalDTO findOne(Long id);

    /**
     * Delete the "id" artical.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<ArticalVO> findAllOrderByDateDesc();

    List<ArticalVO> findAllOrderByLikeDesc();
}
