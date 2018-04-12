package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.ClientUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClientUser and its DTO ClientUserDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ClientUserMapper extends EntityMapper<ClientUserDTO, ClientUser> {



    default ClientUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientUser clientUser = new ClientUser();
        clientUser.setId(id);
        return clientUser;
    }
}
