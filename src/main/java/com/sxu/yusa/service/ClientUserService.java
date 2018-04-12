package com.sxu.yusa.service;

import com.sxu.yusa.domain.ClientUser;
import com.sxu.yusa.service.dto.ClientUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;

/**
 * Service Interface for managing ClientUser.
 */
public interface ClientUserService {

    /**
     * Save a clientUser.
     *
     * @param clientUserDTO the entity to save
     * @return the persisted entity
     */
    ClientUserDTO save(ClientUserDTO clientUserDTO);

    /**
     * Get all the clientUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClientUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clientUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClientUserDTO findOne(Long id);

    /**
     * Delete the "id" clientUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ClientUserDTO login(String phone, String password);

    void uploadFigure(File file,Long id);
}
