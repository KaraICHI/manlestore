package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.ArticalCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ArticalComment and its DTO ArticalCommentDTO.
 */
@Mapper(componentModel = "spring", uses = {ArticalMapper.class, ClientUserMapper.class})
public interface ArticalCommentMapper extends EntityMapper<ArticalCommentDTO, ArticalComment> {

    @Mapping(source = "artical.id", target = "articalId")
    @Mapping(source = "clientUser.id", target = "clientUserId")
    ArticalCommentDTO toDto(ArticalComment articalComment);

    @Mapping(source = "articalId", target = "artical")
    @Mapping(source = "clientUserId", target = "clientUser")
    ArticalComment toEntity(ArticalCommentDTO articalCommentDTO);

    default ArticalComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticalComment articalComment = new ArticalComment();
        articalComment.setId(id);
        return articalComment;
    }
}
