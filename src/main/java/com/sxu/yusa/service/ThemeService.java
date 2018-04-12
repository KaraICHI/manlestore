package com.sxu.yusa.service;

import com.sxu.yusa.service.dto.ThemeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Theme.
 */
public interface ThemeService {

    /**
     * Save a theme.
     *
     * @param themeDTO the entity to save
     * @return the persisted entity
     */
    ThemeDTO save(ThemeDTO themeDTO);

    /**
     * Get all the themes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ThemeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" theme.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ThemeDTO findOne(Long id);

    /**
     * Delete the "id" theme.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
