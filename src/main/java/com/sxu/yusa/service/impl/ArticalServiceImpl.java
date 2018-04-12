package com.sxu.yusa.service.impl;

import com.sxu.yusa.service.ArticalService;
import com.sxu.yusa.domain.Artical;
import com.sxu.yusa.repository.ArticalRepository;
import com.sxu.yusa.service.dto.ArticalDTO;
import com.sxu.yusa.service.mapper.ArticalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Artical.
 */
@Service
@Transactional
public class ArticalServiceImpl implements ArticalService {

    private final Logger log = LoggerFactory.getLogger(ArticalServiceImpl.class);

    private final ArticalRepository articalRepository;

    private final ArticalMapper articalMapper;

    public ArticalServiceImpl(ArticalRepository articalRepository, ArticalMapper articalMapper) {
        this.articalRepository = articalRepository;
        this.articalMapper = articalMapper;
    }

    /**
     * Save a artical.
     *
     * @param articalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ArticalDTO save(ArticalDTO articalDTO) {
        log.debug("Request to save Artical : {}", articalDTO);
        Artical artical = articalMapper.toEntity(articalDTO);
        artical = articalRepository.save(artical);
        return articalMapper.toDto(artical);
    }

    /**
     * Get all the articals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Articals");
        return articalRepository.findAll(pageable)
            .map(articalMapper::toDto);
    }

    /**
     * Get one artical by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArticalDTO findOne(Long id) {
        log.debug("Request to get Artical : {}", id);
        Artical artical = articalRepository.findOne(id);
        return articalMapper.toDto(artical);
    }

    /**
     * Delete the artical by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artical : {}", id);
        articalRepository.delete(id);
    }
}
