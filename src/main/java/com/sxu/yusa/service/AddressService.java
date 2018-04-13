package com.sxu.yusa.service;

import com.sxu.yusa.domain.Address;
import com.sxu.yusa.service.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    AddressDTO save(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AddressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AddressDTO findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<AddressDTO> findByClientUser(long clientUserId);
}
