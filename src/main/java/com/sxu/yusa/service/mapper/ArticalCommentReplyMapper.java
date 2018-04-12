package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.ArticalCommentReplyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ArticalCommentReply and its DTO ArticalCommentReplyDTO.
 */
@Mapper(componentModel = "spring", uses = {ArticalCommentMapper.class, ClientUserMapper.class})
public interface ArticalCommentReplyMapper extends EntityMapper<ArticalCommentReplyDTO, ArticalCommentReply> {

    @Mapping(source = "articalComment.id", target = "articalCommentId")
    @Mapping(source = "clientUser.id", target = "clientUserId")
    ArticalCommentReplyDTO toDto(ArticalCommentReply articalCommentReply);

    @Mapping(source = "articalCommentId", target = "articalComment")
    @Mapping(source = "clientUserId", target = "clientUser")
    ArticalCommentReply toEntity(ArticalCommentReplyDTO articalCommentReplyDTO);

    default ArticalCommentReply fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticalCommentReply articalCommentReply = new ArticalCommentReply();
        articalCommentReply.setId(id);
        return articalCommentReply;
    }
}
