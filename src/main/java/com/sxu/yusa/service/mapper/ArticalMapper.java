package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.ArticalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Artical and its DTO ArticalDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientUserMapper.class})
public interface ArticalMapper extends EntityMapper<ArticalDTO, Artical> {

    @Mapping(source = "clientUser.id", target = "clientUserId")
    ArticalDTO toDto(Artical artical);

    @Mapping(source = "clientUserId", target = "clientUser")
    Artical toEntity(ArticalDTO articalDTO);

    default Artical fromId(Long id) {
        if (id == null) {
            return null;
        }
        Artical artical = new Artical();
        artical.setId(id);
        return artical;
    }
}
